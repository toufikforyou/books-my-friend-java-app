package com.sopnolikhi.booksmyfriend.Design.Ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;
import com.sopnolikhi.booksmyfriend.Design.Adapters.IntroActivity.IntroViewPagerAdapter;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Location.LocationManagerHelper;
import com.sopnolikhi.booksmyfriend.Services.Includes.Location.LocationUpdateListener;
import com.sopnolikhi.booksmyfriend.Services.Models.ViewPager.IntroScreenModel;
import com.sopnolikhi.booksmyfriend.Services.Permissions.Locations;
import com.sopnolikhi.booksmyfriend.Services.Permissions.Notification;
import com.sopnolikhi.booksmyfriend.Services.Permissions.PermissionCallback;
import com.sopnolikhi.booksmyfriend.databinding.ActivityIntroBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity {

    static {
        System.loadLibrary("MyNativeLibrary");
    }

    ActivityIntroBinding activityIntroBinding;
    IntroViewPagerAdapter viewPagerAdapter;
    int position = 0;
    List<IntroScreenModel> arrayList;
    Animation getStartedBtnAnimation;

    public static native void setDeviceId(String deviceId);

    private static native void setLatitude(String latitude);

    private static native void setLongitude(String longitude);

    private static native void setLocationAddress(String locationAddress);

    private static native void setLocationCountryCode(String locationCountry);

    public static void displayLocation(Context context) {
        LocationManagerHelper locationManagerHelper = new LocationManagerHelper(new LocationUpdateListener() {
            @Override
            public void onLocationUpdate(Location location) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressString = address.getAddressLine(0);

                        setLatitude(String.valueOf(location.getLatitude()));
                        setLongitude(String.valueOf(location.getLongitude()));
                        setLocationAddress(addressString);
                        setLocationCountryCode(address.getCountryCode());

                        IntroActivity.setCountryCode(address.getCountryCode(), context);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLocationChanged(@NonNull Location location) {
            }
        });

        locationManagerHelper.requestLocationUpdates(context);
    }

    private static void setCountryCode(String countryCode, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("countryCode", countryCode);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntroBinding = ActivityIntroBinding.inflate(getLayoutInflater());
        // setContentView(R.layout.activity_intro);
        setContentView(activityIntroBinding.getRoot());

        // TODO: Intro Activity Already Show
        if (clearApplicationNone()) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();

            return;
        }

        // TODO:: Location permission

        if (Locations.hasLocationPermissions(getApplicationContext())) {
            displayLocation(getApplicationContext());
        } else {
            Locations.requestLocationPermissions(this);
        }


        // Animation set get Started Button
        getStartedBtnAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.started_button_intro_activity);


        // Array List
        arrayList = new ArrayList<>();
        arrayList.add(new IntroScreenModel("Books Slider", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever", R.drawable.ic_error_1023));
        arrayList.add(new IntroScreenModel("Get notified!", "Stay on top of your trip when better offers or personalized suggestions are found", R.drawable.ic_notification_icon));
        arrayList.add(new IntroScreenModel("Nearby shire", "শাসনতন্ত্রে বা আ\u200Cইনে প্রদত্ত মৌলিক অধিকার লঙ্ঘনের ক্ষেত্রে উপযুক্ত জাতীয় বিচার আদালতের কাছ থেকে কার্যকর প্রতিকার লাভের অধিকার প্রত্যেকের\u200Cই রয়েছে। কা\u200Cউকে\u200Cই খেয়ালখুশীমত গ্রেপ্তার বা অন্তরীণ করা কিংবা নির্বাসন দে\u200Cওয়া যাবে না।", R.drawable.ic_error_1023));
        arrayList.add(new IntroScreenModel("Storage", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever", R.drawable.ic_error_1023));
        arrayList.add(new IntroScreenModel("Nearby shire", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.\nLorem Ipsum has been the industry's standard dummy text ever", R.drawable.ic_error_1023));

        viewPagerAdapter = new IntroViewPagerAdapter(this, arrayList);
        activityIntroBinding.viewPager.setAdapter(viewPagerAdapter);

        // Tab Slider setup
        activityIntroBinding.tabLayout.setupWithViewPager(activityIntroBinding.viewPager);

        // Next Button Click
        activityIntroBinding.nextButton.setOnClickListener(view -> {
            position = activityIntroBinding.viewPager.getCurrentItem();
            if (position < arrayList.size()) {
                position++;
                activityIntroBinding.viewPager.setCurrentItem(position);
            }
        });

        // Animation Button set text and onClick
        activityIntroBinding.animationButton.setText(getResources().getString(R.string.getStartedText));
        activityIntroBinding.animationButton.setOnClickListener(view -> {
            savePreviousData();
            activityIntroBinding.animationButton.setText(getResources().getString(R.string.pleaseWaitText));
            new Handler().postDelayed(() -> {
                activityIntroBinding.animationButton.setEnabled(false);
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                // TODO: Finish this activity;
                finish();
            }, 1000);

        });


        // Slide Last Image To show Get Started Button

        activityIntroBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    getNotificationPermission();
                }
                if (tab.getPosition() == arrayList.size() - 1) {
                    getStartButtonShow();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == arrayList.size() - 1) {
                    getStartButtonHide();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void getNotificationPermission() {
        activityIntroBinding.notificationAllowBtn.setVisibility(View.VISIBLE);
        if (Notification.hasNotificationPermissions(getApplicationContext())) {
            NotificationHandler();
        }
        activityIntroBinding.notificationAllowBtn.setOnClickListener(v -> {
            if (Notification.hasNotificationPermissions(getApplicationContext())) {
                NotificationHandler();
            } else {
                Notification.requestNotificationPermissions(this);
            }
        });
    }

    private void NotificationHandler() {
        activityIntroBinding.notificationAllowBtn.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Locations.onRequestPermissionsResult(requestCode, grantResults, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                displayLocation(getApplicationContext());
            }

            @Override
            public void onPermissionDenied() {
                Locations.handlePermissionResult(IntroActivity.this, grantResults, new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        displayLocation(getApplicationContext());
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.locationRequirePermissionText), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Notification.onRequestPermissionsResult(requestCode, grantResults, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                NotificationHandler();
            }

            @Override
            public void onPermissionDenied() {
                Notification.handlePermissionResult(IntroActivity.this, grantResults, new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        NotificationHandler();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.notificationRequirePermissionText), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getStartButtonHide() {
        activityIntroBinding.animationButton.setVisibility(View.GONE);
        activityIntroBinding.layoutContainer.setVisibility(View.VISIBLE);
    }


    private boolean clearApplicationNone() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IntroLayout", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IntroOpened", false);
    }

    private void savePreviousData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IntroLayout", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IntroOpened", true);
        editor.apply();
    }

    private void getStartButtonShow() {
        activityIntroBinding.animationButton.setVisibility(View.VISIBLE);
        activityIntroBinding.layoutContainer.setVisibility(View.GONE);

        // TODO: SetUp Animation for this button
        activityIntroBinding.animationButton.startAnimation(getStartedBtnAnimation);
    }
}