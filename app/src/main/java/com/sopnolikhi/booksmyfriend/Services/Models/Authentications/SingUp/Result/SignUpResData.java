package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResData {
    @SerializedName("uid")
    @Expose
    private Long uid;
    @SerializedName("full-name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public Long getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
