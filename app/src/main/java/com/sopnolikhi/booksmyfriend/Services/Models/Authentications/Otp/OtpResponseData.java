package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponseData {
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("verify")
    @Expose
    private Boolean verify;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("expired")
    @Expose
    private String expired;

    public String getAccount() {
        return account;
    }

    public Boolean getVerify() {
        return verify;
    }

    public String getMessage() {
        return message;
    }

    public String getExpired() {
        return expired;
    }
}
