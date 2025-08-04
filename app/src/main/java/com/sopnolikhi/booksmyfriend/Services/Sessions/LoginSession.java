package com.sopnolikhi.booksmyfriend.Services.Sessions;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginSession {

    private static final String LOGIN_PREFERENCES = "UserLoginPref";
    private static final String KEY_USER_FULLNAME = "name";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_EXPIRED = "expired";

    static {
        System.loadLibrary("MyNativeLibrary");
    }

    private final SharedPreferences sharedPreferences;

    public LoginSession(Context context) {
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
    }

    private static native void setUserToken(String userToken);

    private static native void setUserId(String userId);

    private static native void setFullName(String fullName);

    public void saveLoginUserInfo(String name, String userid, String token, String expired) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_FULLNAME, name);
        editor.putString(KEY_USER_ID, userid);
        editor.putString(KEY_USER_TOKEN, token);
        editor.putString(KEY_USER_EXPIRED, expired);
        editor.apply();

        setFullName(name);
        setUserToken(token);
        setUserId(userid);
    }

    public boolean isLoggedIn() {
        boolean hasUsername = sharedPreferences.contains(KEY_USER_ID);
        boolean hasToken = sharedPreferences.contains(KEY_USER_TOKEN);

        String expiredTime = sharedPreferences.getString(KEY_USER_EXPIRED, "");

        boolean hasExpired = isExpired(expiredTime);

        return hasUsername && hasToken && !hasExpired;
    }

    private boolean isExpired(String expiredTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date expirationDate = dateFormat.parse(expiredTime);
            Date currentDate = new Date();

            if (currentDate.after(expirationDate)) {
                logout();
            }

            setFullName(sharedPreferences.getString(KEY_USER_FULLNAME, ""));
            setUserToken(sharedPreferences.getString(KEY_USER_TOKEN, ""));
            setUserId(sharedPreferences.getString(KEY_USER_ID, ""));

            return currentDate.after(expirationDate);
        } catch (ParseException e) {
            Log.d("LoginSession", "Error parsing expiration date: " + e.getMessage());
        }
        return true;
    }

    public void logout() {
        setFullName("");
        setUserToken("");
        setUserId("");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_FULLNAME);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_TOKEN);
        editor.remove(KEY_USER_EXPIRED);
        editor.apply();
    }

    public boolean clearApplicationNone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("IntroLayout", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IntroOpened", false);
    }

}
