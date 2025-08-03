package com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account.Service;

import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountCheckRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Account.Common.AccountInfoCheckModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.OtpResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Send.SendOtpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.Otp.Verify.VerifyOtpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.LoginRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.LoginResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.SignUpRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingUp.SignUpResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApiService {

    @POST("/v1/account/check")
    Call<AccountInfoCheckModel> getCheckAccountInfo(@Body AccountCheckRequestModel checkRequestModel);

    @POST("/v1/account/login")
    Call<LoginResponseModel> getLoginAccount(@Body LoginRequestModel loginRequestModel);

    @POST("/v1/account/create")
    Call<SignUpResponseModel> getSignUpAccount(@Body SignUpRequestModel requestModel);

    @POST("/v1/account/otp/send")
    Call<OtpResponseModel> sendOtpCode(@Body SendOtpRequestModel sendOtpRequestModel);

    @POST("/v1/account/otp/verify")
    Call<OtpResponseModel> verifyOtpCode(@Body VerifyOtpRequestModel verifyOtpRequestModel);
}
