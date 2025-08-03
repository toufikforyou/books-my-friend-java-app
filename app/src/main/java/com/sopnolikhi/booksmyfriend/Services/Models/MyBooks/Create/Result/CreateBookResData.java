package com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBookResData {
    @SerializedName("bid")
    @Expose
    private String bid;

    @SerializedName("name")
    @Expose
    private String bName;

    @SerializedName("cover")
    @Expose
    private String cover;

    public String getBid() {
        return bid;
    }

    public String getBName() {
        return bName;
    }

    public String getCover() {
        return cover;
    }
}
