package com.sopnolikhi.booksmyfriend.Services.Includes.Storage;

public class ApiStorage {

    static {
        System.loadLibrary("MyNativeLibrary");
    }

    private static native String getApiUrl();

    private static native String getAuthApiKey();

    private static native String getAuthApiToken();

    private static native String getUserId();

    private static native String getUserTokenKey();

    private static native String getFullName();

    private static native String getUserDeviceId();

    public static String getDeviceId() {
        return getUserDeviceId();
    }

    public static String getApiBaseUrl() {
        return getApiUrl();
    }

    public static String getApiKey() {
        return getAuthApiKey();
    }

    public static String getApiToken() {
        return getAuthApiToken();
    }

    public static String getUserToken() {
        return getUserTokenKey();
    }

    public static String getUserFullName() {
        return getFullName();
    }

    public static String getUid() {
        return getUserId();
    }
}
