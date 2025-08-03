package com.sopnolikhi.booksmyfriend.Services.Models.Common.Request;

import com.google.gson.annotations.SerializedName;
import com.sopnolikhi.booksmyfriend.Services.Includes.Information.DeviceInfo;
import com.sopnolikhi.booksmyfriend.Services.Includes.Information.Location;
import com.sopnolikhi.booksmyfriend.Services.Includes.Storage.ApiStorage;

public abstract class Request {
    @SerializedName("locations")
    private final Locations locations;

    @SerializedName("device-info")
    private final DeviceDetails deviceInfo;

    public Request() {
        this.locations = new Locations(Location.latitude(), Location.longitude(), Location.address());
        this.deviceInfo = new DeviceDetails(ApiStorage.getDeviceId(), DeviceInfo.getAppName(), DeviceInfo.getDeviceType(), DeviceInfo.getOSVersion(), DeviceInfo.getBrowserType(), DeviceInfo.getMobileInfo(), DeviceInfo.getLanguage());
    }
}
