package com.sopnolikhi.booksmyfriend.Services.Models.ViewPager;

public class IntroScreenModel {
    String title,description;
    int screenImage;

    public IntroScreenModel(String title, String description, int screenImage) {
        this.title = title;
        this.description = description;
        this.screenImage = screenImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getScreenImage() {
        return screenImage;
    }
}
