package com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.SignIn;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.Result.LogInResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.SingIn.SignInRepository;

public class SignInViewModel extends AndroidViewModel {
    private final SignInRepository signInRepository;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        this.signInRepository = new SignInRepository();
    }

    public LiveData<ApiResponseModel<LogInResData>> getLogInResult() {
        return signInRepository.getLogInLiveData();
    }

    public void getLogInRequest(String account, String password, Boolean remember) {
        signInRepository.getLogInRequest(account, password, remember);
    }
}
