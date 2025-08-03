package com.sopnolikhi.booksmyfriend.Services.Includes.Information;

public class Location {

    static {
        System.loadLibrary("MyNativeLibrary");
    }

    private static native String getLatitude();

    private static native String getLongitude();

    private static native String getAddress();

    private static native String getLocationCountryCode();


    public static String latitude() {
        return getLatitude();
    }

    public static String longitude() {
        return getLongitude();
    }

    public static String address() {
        return getAddress();
    }

    public static String locationCountryCode() {
        return getLocationCountryCode();
    }
}
