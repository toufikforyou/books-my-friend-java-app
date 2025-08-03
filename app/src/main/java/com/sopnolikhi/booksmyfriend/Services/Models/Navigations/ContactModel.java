package com.sopnolikhi.booksmyfriend.Services.Models.Navigations;

public class ContactModel {
    private final String profile;
    private final String name;
    private final String number;

    public ContactModel(String profile, String name, String number) {
        this.profile = profile;
        this.name = name;
        this.number = number;
    }

    public String getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
