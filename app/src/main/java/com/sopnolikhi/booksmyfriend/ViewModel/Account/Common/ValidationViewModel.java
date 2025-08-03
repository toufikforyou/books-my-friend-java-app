package com.sopnolikhi.booksmyfriend.ViewModel.Account.Common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sopnolikhi.booksmyfriend.Services.Validations.Validate;

public class ValidationViewModel extends AndroidViewModel {
    public ValidationViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isInputEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public boolean isNameValid(String name) {
        // Check if the input meets the criteria
        return !Validate.isNameValid(name);
    }

    public boolean accountValid(String account) {
        return !Validate.isUserNameEmailPhone(account);
    }

    public boolean isEmailOrMobileValid(String emailMobile) {
        return !Validate.isEmailOrMobileValid(emailMobile);
    }

    public boolean passwordValid(String password) {
        return !Validate.isPasswordValid(password);
    }

    public boolean passwordConfirm(String password, String confirm) {
        return !Validate.confirmPassword(password, confirm);
    }


}
