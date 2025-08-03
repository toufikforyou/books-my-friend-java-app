package com.sopnolikhi.booksmyfriend.Services.Repositories.MyBooks.Create;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.BookCreateRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.BookCreateResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.Result.CreateBookResData;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Books.BooksApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Books.Service.BooksApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookRepository {
    private final BooksApiService booksApiService;
    private final Context context;

    private MutableLiveData<ApiResponseModel<CreateBookResData>> createBookLiveData;

    public CreateBookRepository(Context context) {
        this.context = context;
        this.booksApiService = BooksApiInstance.getRetrofit().create(BooksApiService.class);
    }

    public MutableLiveData<ApiResponseModel<CreateBookResData>> getCreateBookLiveData() {
        if (createBookLiveData == null) {
            createBookLiveData = new MutableLiveData<>();
        }
        return createBookLiveData;
    }


    public void createBookRequest(String name, String info, File coverImage) {
        // Assuming 'coverImage' is your File object representing the large image
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), coverImage);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("cover", coverImage.getName(), requestBody);




        // MultipartBody.Part filePart = MultipartBody.Part.createFormData("cover", coverImage.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), coverImage));

        BookCreateRequestModel bookCreateRequestModel = new BookCreateRequestModel(name, info);

        createBookLiveData.postValue(new ApiResponseModel.Loading<>());

        booksApiService.createBook(filePart, bookCreateRequestModel).enqueue(new Callback<BookCreateResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<BookCreateResponseModel> call, @NonNull Response<BookCreateResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createBookLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "Create book successfully", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        createBookLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookCreateResponseModel> call, @NonNull Throwable t) {
                createBookLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }

    public void clearCreateBookData() {
        if (createBookLiveData != null) {
            createBookLiveData = null;
        }
    }
}
