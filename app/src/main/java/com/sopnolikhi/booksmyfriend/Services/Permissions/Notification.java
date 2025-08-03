package com.sopnolikhi.booksmyfriend.Services.Permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;

public class Notification {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = KeyValue.NOTIFICATION_PERMISSION_CODE;

    public static boolean hasNotificationPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int notification = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS);

            return notification == PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED;
        }

        return true; // Permissions are implicitly granted on devices with SDK < 26
    }

    public static void requestNotificationPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception or show a message to the user
            }
        }
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, PermissionCallback callback) {
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied();
            }
        }
    }


    public static void handlePermissionResult(Activity activity, int[] grantResults, PermissionCallback callback) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
            // User denied permission, but did not check "never ask again"
            callback.onPermissionDenied();
        } else {
            // User denied permission and checked "never ask again"
            showPermissionExplanationDialog(activity);
        }
    }

    private static void showPermissionExplanationDialog(Activity activity) {
        new AlertDialog.Builder(activity).setTitle("Permission Required").setMessage("This app requires notifications permission to function properly. You can grant the permission in the app settings.").setPositiveButton("Open Settings", (dialog, which) -> openAppSettings(activity)).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }

    private static void openAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
    }
}
