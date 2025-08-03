package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class LoginRequestModel extends Request {

    @SerializedName("account")
    private final String account;
    @SerializedName("password")
    private final String password;
    @SerializedName("remember")
    private final Boolean rememberPassword;

    public LoginRequestModel(String account, String password, Boolean rememberPassword) {
        super();
        this.account = account;
        this.password = password;
        this.rememberPassword = rememberPassword;

    }
}
