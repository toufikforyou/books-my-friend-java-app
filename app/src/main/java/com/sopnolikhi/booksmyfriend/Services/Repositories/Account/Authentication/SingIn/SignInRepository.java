package com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.SingIn;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.LoginRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.LoginResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.Result.LogInResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.AccountApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.Service.AccountApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInRepository {
    private final AccountApiService accountApiService;

    MutableLiveData<ApiResponseModel<LogInResData>> logInLiveData;

    public SignInRepository() {
        this.accountApiService = AccountApiInstance.getRetrofit().create(AccountApiService.class);
    }

    public MutableLiveData<ApiResponseModel<LogInResData>> getLogInLiveData() {
        if (logInLiveData == null) {
            logInLiveData = new MutableLiveData<>();
        }
        return logInLiveData;
    }

    public void getLogInRequest(String account, String password, Boolean remember) {
        LoginRequestModel loginRequestModel = new LoginRequestModel(account, password, remember);

        logInLiveData.postValue(new ApiResponseModel.Loading<>());

        // Debugging purpose: Mocked login for specific account
        if (account.equals("toufikforyou@gmail.com") && password.equals("#password")) {
            LogInResData logInResData = new LogInResData();
            logInResData.setFullName("MH TOUFIK");
            logInResData.setUserId("123546789");
            logInResData.setExpired("2025-10-01 12:00:00");
            logInResData.setToken("mocked_token_1234567890");

            logInLiveData.postValue(new ApiResponseModel.SuccessCode<>(1000, "Mocked Login Success", logInResData));
            return;
        }
        // Actual API call for login
        accountApiService.getLoginAccount(loginRequestModel).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logInLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "LogIn Result Found", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        logInLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                logInLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }
}
