package com.sopnolikhi.booksmyfriend.Services.Models.Common.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class MainResponseModel<T> {
    @SerializedName("code")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private T result;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public Integer getStatusCode() {
        return statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getLanguage() {
        return language;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
