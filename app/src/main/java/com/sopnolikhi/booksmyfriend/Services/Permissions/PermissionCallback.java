package com.sopnolikhi.booksmyfriend.Services.Permissions;

public interface PermissionCallback {
    void onPermissionGranted();

    void onPermissionDenied();
}
