package com.sopnolikhi.booksmyfriend.Services.Models.Authentications.SingIn.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInResData {
    @SerializedName("full-name")
    @Expose
    private String fullName;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expired")
    @Expose
    private String expired;

    public String getFullName() {
        return fullName;
    }

    public String getUserId() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getExpired() {
        return expired;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserId(String uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

}
