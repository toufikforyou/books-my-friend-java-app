package com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.SignUp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.Result.SignUpResData;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Repositories.Account.Authentication.SingUp.SignUpRepository;

public class SignUpViewModel extends AndroidViewModel {
    private final SignUpRepository signUpRepository;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        this.signUpRepository = new SignUpRepository(application);
    }

    public LiveData<ApiResponseModel<SignUpResData>> getSignUpData() {
        return signUpRepository.getSignUpLiveData();
    }

    public void getSignUpRequest(String name, String account, String gender, String dob, String password) {
        signUpRepository.getSignUpRequest(name, account, gender, dob, password);
    }

    public void clearSignUpData() {
        signUpRepository.clearSingUpData();
    }
}
