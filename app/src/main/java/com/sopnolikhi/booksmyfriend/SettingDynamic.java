package com.sopnolikhi.booksmyfriend;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.android.material.color.DynamicColors;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;

public class SettingDynamic extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // DynamicColors.applyToActivitiesIfAvailable(this);

        // TODO:: Application Preference on change then upload on application load
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // TODO:: Application change night, dark and system mode;
        UserSetting.NightModeApply(sharedPreferences.getString(UserSetting.NIGHT_MODE, "system"));

        // TODO:: Application change language, dark and system mode;
        String defaultLanguage = sharedPreferences.getString("countryCode", "").equals("BD") ? "bn" : "en";
        UserSetting.SETTING_LANGUAGE_USER_SET = sharedPreferences.getString(UserSetting.SETTING_LANGUAGE, defaultLanguage).equals("any") ? defaultLanguage : sharedPreferences.getString(UserSetting.SETTING_LANGUAGE, defaultLanguage);

    }
}
