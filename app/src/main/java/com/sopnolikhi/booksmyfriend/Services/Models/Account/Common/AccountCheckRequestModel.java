package com.sopnolikhi.booksmyfriend.Services.Models.Account.Common;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class AccountCheckRequestModel extends Request {
    @SerializedName("account")
    private final String account;

    public AccountCheckRequestModel(String account) {
        super();
        this.account = account;
    }
}
