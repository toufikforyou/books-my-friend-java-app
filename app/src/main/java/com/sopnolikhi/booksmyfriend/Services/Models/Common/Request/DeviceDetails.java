package com.sopnolikhi.booksmyfriend.Services.Models.Common.Request;

import com.google.gson.annotations.SerializedName;

public class DeviceDetails {

    @SerializedName("device-id")
    private final String getDeviceId;

    @SerializedName("source")
    private final String appName;

    @SerializedName("type")
    private final String deviceType;

    @SerializedName("os")
    private final String osVersion;

    @SerializedName("user-agent")
    private final String userAgent;

    @SerializedName("information")
    private final String information;

    @SerializedName("language")
    private final String language;

    public DeviceDetails(String getDeviceId, String appName, String deviceType, String osVersion, String userAgent, String information, String language) {
        this.getDeviceId = getDeviceId;
        this.appName = appName;
        this.deviceType = deviceType;
        this.osVersion = osVersion;
        this.userAgent = userAgent;
        this.information = information;
        this.language = language;
    }
}
