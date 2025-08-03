package com.sopnolikhi.booksmyfriend.Design.Dialogs.Review;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class InAppReview {
    public static void Review(Context context) {

        ReviewManager manager = ReviewManagerFactory.create(context);

        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                assert reviewInfo != null;
                Task<Void> voidTask = manager.launchReviewFlow((Activity) context, reviewInfo);

                voidTask.addOnSuccessListener(unused -> Toast.makeText(context, "রেটিং দেওয়ার জন্য ধন্যবাদ.", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(context, "Something ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
