package com.sopnolikhi.booksmyfriend.Services.Includes.Information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.Locale;

public class DeviceInfo {
    public static String getAppName() {
        return "Books My Friend";
    }

    public static String getDeviceType() {
        return Build.MANUFACTURER + " " + Build.MODEL;

    }

    public static String getDeviceSN() {
        return Build.SERIAL;
    }

    public static String getSecureAndroidID(Context context) {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Fallback to generating a UUID if Android ID is null (this should be rare)
        if (androidId == null) {
            androidId = generateUUID();
        }

        return androidId;
    }

    private static String generateUUID() {
        return "SL"+java.util.UUID.randomUUID().toString();
    }

    public static String getOSVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String getMobileInfo() {
        return "Display " + Build.DISPLAY + ", Device " + Build.DEVICE + ", ID " + Build.ID + ", Brand " + Build.BRAND;
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage(); // return context.getResources().getConfiguration().locale.getLanguage();
    }

    public static String getBrowserType() {
        // Implement your browser detection logic here if necessary
        return "N/A";
    }
}
