package com.sopnolikhi.booksmyfriend.Services.Models.Account.Common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountInfoResData {
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("account")
    @Expose
    private Integer account;
    @SerializedName("full-name")
    @Expose
    private String fullName;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("date-of-birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("date")
    @Expose
    private String date;


    public String getUid() {
        return uid;
    }

    public Integer getAccount() {
        return account;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfile() {
        return profile;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDate() {
        return date;
    }
}
