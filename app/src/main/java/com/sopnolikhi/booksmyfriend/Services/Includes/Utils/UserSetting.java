package com.sopnolikhi.booksmyfriend.Services.Includes.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class UserSetting {
    public static String NIGHT_MODE = "night_mode";
    public static String SETTING_LANGUAGE = "language";
    public static String SETTING_LANGUAGE_USER_SET = "";

    public static void NightModeApply(String mode) {
        int nightMode;
        switch (mode) {
            case "light":
                nightMode = AppCompatDelegate.MODE_NIGHT_NO;
                break;
            case "dark":
                nightMode = AppCompatDelegate.MODE_NIGHT_YES;
                break;
            default:
                nightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                break;
        }
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    public static void UpdateApplicationLanguage(Context context, String language) {
        UserSetting.SETTING_LANGUAGE_USER_SET = language;
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
