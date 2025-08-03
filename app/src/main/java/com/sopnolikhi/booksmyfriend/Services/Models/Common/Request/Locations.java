package com.sopnolikhi.booksmyfriend.Services.Models.Common.Request;

import com.google.gson.annotations.SerializedName;

public class Locations {
    @SerializedName("latitude")
    private final String latitude;

    @SerializedName("longitude")
    private final String longitude;

    @SerializedName("address")
    private final String address;

    public Locations(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
