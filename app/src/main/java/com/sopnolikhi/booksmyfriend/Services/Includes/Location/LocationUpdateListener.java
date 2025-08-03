package com.sopnolikhi.booksmyfriend.Services.Includes.Location;

import android.location.Location;

import com.google.android.gms.location.LocationListener;

public interface LocationUpdateListener extends LocationListener {
    void onLocationUpdate(Location location);
}
