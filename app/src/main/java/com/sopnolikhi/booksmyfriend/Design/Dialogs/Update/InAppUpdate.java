package com.sopnolikhi.booksmyfriend.Design.Dialogs.Update;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class InAppUpdate {
    private static final int UPDATE_IN_APP_CODE = 255;

    public static void AppUpdate(Context context) {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.

                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, (Activity) context, UPDATE_IN_APP_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(context, "এখনো আপডেট আসে নাই!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
