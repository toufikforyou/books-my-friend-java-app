package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class SignUpRequestModel extends Request {
    @SerializedName("full-name")
    private final String fullName;
    @SerializedName("account")
    private final String account;
    @SerializedName("gender")
    private final String gender;
    @SerializedName("date-of-birth")
    private final String dob;
    @SerializedName("password")
    private final String password;

    public SignUpRequestModel(String fullName, String account, String gender, String dob, String password) {
        super();
        this.fullName = fullName;
        this.account = account;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
    }
}
