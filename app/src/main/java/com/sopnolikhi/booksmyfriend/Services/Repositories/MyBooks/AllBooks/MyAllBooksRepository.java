package com.sopnolikhi.booksmyfriend.Services.Repositories.MyBooks.AllBooks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.MyAllBooksResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.Result.MyAllBooksResData;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Books.BooksApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Books.Service.BooksApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAllBooksRepository {
    private final BooksApiService booksApiService;

    private final Context context;

    private MutableLiveData<ApiResponseModel<List<MyAllBooksResData>>> myAllBooksLiveData;

    public MyAllBooksRepository(Context context) {
        this.context = context;
        this.booksApiService = BooksApiInstance.getRetrofit().create(BooksApiService.class);
    }

    public MutableLiveData<ApiResponseModel<List<MyAllBooksResData>>> getMyAllBooksLiveData() {
        if (myAllBooksLiveData == null) {
            myAllBooksLiveData = new MutableLiveData<>();
        }
        return myAllBooksLiveData;
    }

    public void requestMyBooks() {
        myAllBooksLiveData.postValue(new ApiResponseModel.Loading<>());

        booksApiService.getAllMyBooks().enqueue(new Callback<MyAllBooksResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<MyAllBooksResponseModel> call, @NonNull Response<MyAllBooksResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAllBooksLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "Books found successfully", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        myAllBooksLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyAllBooksResponseModel> call, @NonNull Throwable t) {
                myAllBooksLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }


}
