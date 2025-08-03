package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Send;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class SendOtpRequestModel extends Request {

    @SerializedName("account")
    private final String account;

    @SerializedName("full-name")
    private final String fullName;

    public SendOtpRequestModel(String account, String fullName) {
        super();
        this.account = account;
        this.fullName = fullName;
    }
}
