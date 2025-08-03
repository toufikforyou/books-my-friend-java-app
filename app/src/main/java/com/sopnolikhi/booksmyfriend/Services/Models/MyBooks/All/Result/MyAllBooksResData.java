package com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAllBooksResData {
    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("bname")
    @Expose
    private String bname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("eprice")
    @Expose
    private String eprice;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("update")
    @Expose
    private String update;

    public String getBid() {
        return bid;
    }

    public String getBname() {
        return bname;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    public String getCover() {
        return cover;
    }

    public String getPrice() {
        return price;
    }

    public String getEprice() {
        return eprice;
    }

    public String getUid() {
        return uid;
    }

    public String getPage() {
        return page;
    }

    public String getSubject() {
        return subject;
    }

    public String getAbout() {
        return about;
    }

    public String getUpdate() {
        return update;
    }
}
