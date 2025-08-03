package com.sopnolikhi.booksmyfriend.Services.Permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;

public class Nearby {
    private static final int NEARBY_DEVICES_PERMISSION_REQUEST_CODE = KeyValue.NEARBY_DEVICES_PERMISSION_CODE;

    public static boolean hasNearbyPermissions(Context context) {
        int bluetoothPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            int connectPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT);

            return bluetoothPermission == PackageManager.PERMISSION_GRANTED && connectPermission == PackageManager.PERMISSION_GRANTED;
        }

        return bluetoothPermission == PackageManager.PERMISSION_GRANTED;
    }


    public static void requestNearbyPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_CONNECT}, NEARBY_DEVICES_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH}, NEARBY_DEVICES_PERMISSION_REQUEST_CODE);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, PermissionCallback callback) {
        if (requestCode == NEARBY_DEVICES_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied();
            }
        }
    }


}
