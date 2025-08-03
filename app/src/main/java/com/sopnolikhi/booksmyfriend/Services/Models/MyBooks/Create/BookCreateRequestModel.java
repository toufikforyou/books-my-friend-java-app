package com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.Request.Request;

public class BookCreateRequestModel extends Request {

    @SerializedName("name")
    private final String bookName;

    @SerializedName("description")
    private final String bookInfo;

    public BookCreateRequestModel(String bookName, String bookInfo) {
        super();
        this.bookName = bookName;
        this.bookInfo = bookInfo;
    }
}
