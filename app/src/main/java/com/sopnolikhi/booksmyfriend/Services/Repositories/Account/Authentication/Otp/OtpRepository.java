package com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.Otp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.OtpResponseData;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.OtpResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Send.SendOtpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Verify.VerifyOtpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.AccountApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.Service.AccountApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {
    private final AccountApiService accountApiService;
    private final Context context;

    private MutableLiveData<ApiResponseModel<OtpResponseData>> sendOtpLiveData;

    private MutableLiveData<ApiResponseModel<OtpResponseData>> verifyOtpLiveData;

    public OtpRepository(Context context) {
        this.context = context;
        this.accountApiService = AccountApiInstance.getRetrofit().create(AccountApiService.class);
    }

    public MutableLiveData<ApiResponseModel<OtpResponseData>> getSendOtpLiveData() {
        if (sendOtpLiveData == null) {
            sendOtpLiveData = new MutableLiveData<>();
        }
        return sendOtpLiveData;
    }

    public MutableLiveData<ApiResponseModel<OtpResponseData>> getVerifyOtpLiveData() {
        if (verifyOtpLiveData == null) {
            verifyOtpLiveData = new MutableLiveData<>();
        }
        return verifyOtpLiveData;
    }

    public void clearSendData() {
        if (sendOtpLiveData != null) {
            sendOtpLiveData = null;
        }
    }

    public void clearVerifyData() {
        if (verifyOtpLiveData != null) {
            verifyOtpLiveData = null;
        }
    }

    public void sendOtpRequestApi(String name, String account) {
        SendOtpRequestModel requestModel = new SendOtpRequestModel(account, name);

        sendOtpLiveData.postValue(new ApiResponseModel.Loading<>());

        accountApiService.sendOtpCode(requestModel).enqueue(new Callback<OtpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpResponseModel> call, @NonNull Response<OtpResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sendOtpLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "LogIn Result Found", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        sendOtpLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OtpResponseModel> call, @NonNull Throwable t) {
                sendOtpLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });

    }

    public void setVerifyOtpLiveData(String account, String otpCode) {
        VerifyOtpRequestModel verifyOtpResModel = new VerifyOtpRequestModel(account, otpCode);

        verifyOtpLiveData.postValue(new ApiResponseModel.Loading<>());

        accountApiService.verifyOtpCode(verifyOtpResModel).enqueue(new Callback<OtpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpResponseModel> call, @NonNull Response<OtpResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    verifyOtpLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "LogIn Result Found", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        verifyOtpLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OtpResponseModel> call, @NonNull Throwable t) {
                verifyOtpLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }

}
