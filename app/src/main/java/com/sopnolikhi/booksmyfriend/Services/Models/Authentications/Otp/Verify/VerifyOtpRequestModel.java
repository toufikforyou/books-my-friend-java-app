package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Verify;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class VerifyOtpRequestModel extends Request {
    @SerializedName("account")
    private final String account;

    @SerializedName("otp")
    private final String otpCode;

    public VerifyOtpRequestModel(String account, String otpCode) {
        super();
        this.account = account;
        this.otpCode = otpCode;
    }
}
