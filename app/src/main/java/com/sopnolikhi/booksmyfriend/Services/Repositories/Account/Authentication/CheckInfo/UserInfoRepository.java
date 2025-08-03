package com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.CheckInfo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountCheckRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountInfoCheckModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountInfoResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.AccountApiInstance;
import com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.Service.AccountApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRepository {
    private final AccountApiService accountApiService;
    private final Context context;

    private MutableLiveData<ApiResponseModel<AccountInfoResData>> accountInfoLiveData;

    public UserInfoRepository(Context context) {
        this.context = context;
        this.accountApiService = AccountApiInstance.getRetrofit().create(AccountApiService.class);
    }

    public MutableLiveData<ApiResponseModel<AccountInfoResData>> getAccountInfo() {
        if (accountInfoLiveData == null) {
            accountInfoLiveData = new MutableLiveData<>();
        }
        return accountInfoLiveData;
    }

    public void clearData(){
        if(accountInfoLiveData !=null){
            accountInfoLiveData = null;
        }
    }

    public void getAccountInfoRequest(String account) {
        AccountCheckRequestModel requestModel = new AccountCheckRequestModel(account);

        accountInfoLiveData.postValue(new ApiResponseModel.Loading<>());

        accountApiService.getCheckAccountInfo(requestModel).enqueue(new Callback<AccountInfoCheckModel>() {
            @Override
            public void onResponse(@NonNull Call<AccountInfoCheckModel> call, @NonNull Response<AccountInfoCheckModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accountInfoLiveData.postValue(new ApiResponseModel.SuccessCode<>(response.body().getStatusCode(), "Account info found", response.body().getResult()));
                }

                if (response.errorBody() != null) {
                    try {
                        JSONObject errJson = new JSONObject(response.errorBody().string());
                        accountInfoLiveData.postValue(new ApiResponseModel.ErrorCode<>(errJson.getInt("code"), errJson.getString("result"), null));
                    } catch (JSONException | IOException e) {
                        // handle JSON exception
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountInfoCheckModel> call, @NonNull Throwable t) {
                accountInfoLiveData.postValue(new ApiResponseModel.ErrorCode<>(1053, "702: " + t.getMessage(), null));
            }
        });
    }
}
