package com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Notification;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sopnolikhi.booksmyfriend.databinding.ActivityNotificationShowBinding;

import java.util.List;

public class NotificationShowActivity extends AppCompatActivity {

    ActivityNotificationShowBinding nfShowBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfShowBinding = ActivityNotificationShowBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_notification_show);
        setContentView(nfShowBinding.getRoot());


        // Retrieve data from the intent extras
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");
        String imageUrl = getIntent().getStringExtra("image"); // Assuming you pass an image URL

        nfShowBinding.customNotificationTitle.setText(title);
        nfShowBinding.customNotificationMessage.setText(message);

        // Load and display the image using a library like Picasso or Glide
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .skipMemoryCache(true) // Disable memory caching
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                    .into(nfShowBinding.customNotificationImage);
        }
    }

    @Override
    public void onBackPressed() {
        // Check if any activity is already open in the task's back stack
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();

        if (tasks.size() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}