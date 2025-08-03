package com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.SingUp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.Result.SignUpResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.SignUpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.SignUpResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.AccountApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.Service.AccountApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    private final AccountApiService accountApiService;
    private final Context context;

    private MutableLiveData<ApiResponseModel<SignUpResData>> signUpLiveData;

    public SignUpRepository(Context context) {
        this.context = context;
        this.accountApiService = AccountApiInstance.getRetrofit().create(AccountApiService.class);
    }

    public MutableLiveData<ApiResponseModel<SignUpResData>> getSignUpLiveData() {
        if (signUpLiveData == null) {
            signUpLiveData = new MutableLiveData<>();
        }
        return signUpLiveData;
    }

    public void getSignUpRequest(String name, String account, String gender, String dob, String password) {
        SignUpRequestModel requestModel = new SignUpRequestModel(name, account, gender, dob, password);

        signUpLiveData.postValue(new ApiResponseModel.Loading<>());

        accountApiService.getSignUpAccount(requestModel).enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponseModel> call, @NonNull Response<SignUpResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    signUpLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "LogIn Result Found", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        signUpLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponseModel> call, @NonNull Throwable t) {
                signUpLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }

    public void clearSingUpData() {
        if (signUpLiveData != null) {
            signUpLiveData = null;
        }
    }
}
