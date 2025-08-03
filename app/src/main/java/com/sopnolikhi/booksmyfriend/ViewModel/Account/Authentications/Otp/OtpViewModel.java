package com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.Otp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.OtpResponseData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.Otp.OtpRepository;

public class OtpViewModel extends AndroidViewModel {
    private final OtpRepository otpRepository;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        this.otpRepository = new OtpRepository(application);
    }

    public LiveData<ApiResponseModel<OtpResponseData>> sendOtpResponse() {
        return otpRepository.getSendOtpLiveData();
    }

    public LiveData<ApiResponseModel<OtpResponseData>> verifyOtpResponse() {
        return otpRepository.getVerifyOtpLiveData();
    }

    public void sendOtpRequest(String name, String account) {
        otpRepository.sendOtpRequestApi(name, account);
    }

    public void verifyOtpRequest(String account, String otp) {
        otpRepository.setVerifyOtpLiveData(account, otp);
    }

    public void clearSendData() {
        otpRepository.clearSendData();
    }

    public void clearVerifyData() {
        otpRepository.clearVerifyData();
    }
}
