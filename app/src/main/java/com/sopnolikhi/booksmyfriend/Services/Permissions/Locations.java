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

import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;

public class Locations {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = KeyValue.LOCATION_PERMISSION_CODE;
    private static final int REQUEST_ENABLE_GPS = KeyValue.REQUEST_ENABLE_GPS_CODE;

    public static boolean hasLocationPermissions(Context context) {
        int fineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        return fineLocationPermission == PackageManager.PERMISSION_GRANTED && coarseLocationPermission == PackageManager.PERMISSION_GRANTED;

    }


    public static void requestLocationPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    public static void requestGPSEnabled(Context context) {
        ((Activity) context).startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS);
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, PermissionCallback callback) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied();
            }
        }
    }

    public static void handlePermissionResult(Activity activity, int[] grantResults, PermissionCallback callback) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // User denied permission, but did not check "never ask again"
            callback.onPermissionDenied();
        } else {
            // User denied permission and checked "never ask again"
            showPermissionExplanationDialog(activity);
        }
    }

    private static void showPermissionExplanationDialog(Activity activity) {
        new AlertDialog.Builder(activity).setTitle("Permission Required").setMessage("This app requires location permission to function properly. You can grant the permission in the app settings.").setPositiveButton("Open Settings", (dialog, which) -> openAppSettings(activity)).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }

    private static void openAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, LOCATION_PERMISSION_REQUEST_CODE);
    }
}
