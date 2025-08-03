package com.sopnolikhi.booksmyfriend.Design.Ui.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Authentication.AuthUserActivity;
import com.sopnolikhi.booksmyfriend.MainActivity;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Information.DeviceInfo;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;
import com.sopnolikhi.booksmyfriend.Services.Notifications.Push.NotificationHelper;
import com.sopnolikhi.booksmyfriend.Services.Permissions.Locations;
import com.sopnolikhi.booksmyfriend.Services.Sessions.LoginSession;

public class WelcomeActivity extends AppCompatActivity {
    private LoginSession loginSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        // Keep the splash screen visible for this Activity.
        splashScreen.setKeepOnScreenCondition(() -> true);

        // TODO:: Device id save for all application
        IntroActivity.setDeviceId(DeviceInfo.getSecureAndroidID(getApplicationContext()));

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> Log.d("AAAA", s)).addOnFailureListener(e -> {
        });

        // Create the notification channels
        NotificationHelper.createNotificationChannel(getApplicationContext(), getResources().getString(R.string.default_notification_channel_id), getResources().getString(R.string.default_notification_channel_name), getResources().getString(R.string.default_notification_channel_desc));
        NotificationHelper.createNotificationChannel(getApplicationContext(), getResources().getString(R.string.announcement_notification_channel_id), getResources().getString(R.string.announcement_notification_channel_name), getResources().getString(R.string.announcement_notification_channel_desc));
        NotificationHelper.createNotificationChannel(getApplicationContext(), getResources().getString(R.string.create_book_notification_channel_id), getResources().getString(R.string.create_book_notification_channel_name), getResources().getString(R.string.create_book_notification_channel_desc));

        if (loginSession == null) {
            loginSession = new LoginSession(getApplicationContext());
        }

        if (!loginSession.clearApplicationNone(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            finish();
            return;
        }

        // TODO:: Location permission
        if (Locations.hasLocationPermissions(getApplicationContext())) {
            IntroActivity.displayLocation(getApplicationContext());
        }


        if (loginSession.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), AuthUserActivity.class));
            finish();
        }
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        // TODO:: LANGUAGE CHANGE
        UserSetting.UpdateApplicationLanguage(this, UserSetting.SETTING_LANGUAGE_USER_SET);
    }
}