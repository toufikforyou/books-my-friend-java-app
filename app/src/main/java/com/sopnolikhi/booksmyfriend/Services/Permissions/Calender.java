package com.sopnolikhi.booksmyfriend.Services.Permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;

public class Calender {
    private static final int CALENDER_PERMISSION_REQUEST_CODE = KeyValue.CALENDER_PERMISSION_CODE;

    public static boolean hasCalenderPermissions(Context context) {
        int readPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR);
        int writePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR);

        return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCalenderPermissions(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, CALENDER_PERMISSION_REQUEST_CODE);
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, PermissionCallback callback) {
        if (requestCode == CALENDER_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied();
            }
        }
    }

    public static void handlePermissionResult(Activity activity, int[] grantResults, PermissionCallback callback) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CALENDAR) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_CALENDAR)) {
            // User denied permission, but did not check "never ask again"
            callback.onPermissionDenied();
        } else {
            // User denied permission and checked "never ask again"
            showPermissionExplanationDialog(activity);
        }
    }

    private static void showPermissionExplanationDialog(Activity activity) {
        new AlertDialog.Builder(activity).setTitle("Permission Required").setMessage("This app requires calender permission to function properly. You can grant the permission in the app settings.").setPositiveButton("Open Settings", (dialog, which) -> openAppSettings(activity)).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }

    private static void openAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, CALENDER_PERMISSION_REQUEST_CODE);
    }
}
