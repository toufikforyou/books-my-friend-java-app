package com.sopnolikhi.booksmyfriend.Services.Includes.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LocationManagerHelper {

    private final LocationUpdateListener locationUpdateListener;

    public LocationManagerHelper(LocationUpdateListener listener) {
        this.locationUpdateListener = listener;
    }

    public void requestLocationUpdates(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permissions here
                return;
            }

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        if (locationUpdateListener != null) {
                            locationUpdateListener.onLocationUpdate(location);
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // Handle status changes if needed
                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {
                        // Handle provider enabled if needed
                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                        // Handle provider disabled if needed
                    }
                });
            } else {
                // Prompt the user to enable GPS
                Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
            }
        }
    }
}