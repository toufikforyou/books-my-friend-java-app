package com.sopnolikhi.booksmyfriend.Services.Firebase.FCM;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sopnolikhi.booksmyfriend.Services.Notifications.Push.NotificationHelper;

import java.util.Objects;

public class FirebaseMessages extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getNotification() != null) {
            NotificationHelper.showNotification(this, message);
        }
    }

    @Override
    public void handleIntent(Intent intent) {
        try {
            if (intent.getExtras() != null) {
                RemoteMessage.Builder builder = new RemoteMessage.Builder("MessagingService");

                for (String key : intent.getExtras().keySet()) {
                    builder.addData(key, Objects.requireNonNull(intent.getExtras().get(key)).toString());
                }

                onMessageReceived(builder.build());
            } else {
                super.handleIntent(intent);
            }
        } catch (Exception e) {
            super.handleIntent(intent);
        }
    }
}