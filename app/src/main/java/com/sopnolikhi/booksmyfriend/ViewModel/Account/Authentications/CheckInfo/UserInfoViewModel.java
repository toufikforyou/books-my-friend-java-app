package com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.CheckInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountInfoResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.CheckInfo.UserInfoRepository;

public class UserInfoViewModel extends AndroidViewModel {
    private final UserInfoRepository userInfoRepository;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        this.userInfoRepository = new UserInfoRepository(application);
    }

    public LiveData<ApiResponseModel<AccountInfoResData>> getAccountInfoData() {
        return userInfoRepository.getAccountInfo();
    }
    public void clearOtpMutableLiveData(){
        userInfoRepository.clearData();
    }
    public void requestAccountInfo(String account) {
        userInfoRepository.getAccountInfoRequest(account);
    }
}
