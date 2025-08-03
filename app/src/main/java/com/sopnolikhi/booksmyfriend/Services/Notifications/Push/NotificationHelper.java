package com.sopnolikhi.booksmyfriend.Services.Notifications.Push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Notification.NotificationShowActivity;
import com.sopnolikhi.booksmyfriend.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class NotificationHelper {

    public static final String TAG = "ASAS";

    public static void showNotification(Context context, RemoteMessage message) {
        if (message.getNotification() != null) {

            String channelId = message.getNotification().getChannelId();
            String notificationChannelId = context.getResources().getString(R.string.default_notification_channel_id);

            if (channelId != null) {
                if (channelId.equals(context.getResources().getString(R.string.announcement_notification_channel_id))) {
                    notificationChannelId = channelId;
                } else if (channelId.equals(context.getResources().getString(R.string.create_book_notification_channel_id))) {
                    notificationChannelId = channelId;
                } else {
                    notificationChannelId = context.getResources().getString(R.string.default_notification_channel_id);
                }
            }

            notifyNotification(context, notificationChannelId, message);
        }
    }

    public static void notifyNotification(Context context, String channelId, RemoteMessage message) {
        if (message.getNotification() != null) {

            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();

            // Set the notification sound
            Uri defaultSoundUri = Uri.parse("file:///android_asset/notification.mp3");

            if (defaultSoundUri == null) {
                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
            notificationBuilder.setSmallIcon(R.drawable.books_my_friend_logo);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(body);
            notificationBuilder.setSound(channelId.equals(context.getResources().getString(R.string.default_notification_channel_id)) ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) : defaultSoundUri);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setPriority(channelId.equals(context.getResources().getString(R.string.default_notification_channel_id)) ? NotificationCompat.PRIORITY_DEFAULT : NotificationCompat.PRIORITY_MAX);


            if (message.getData().containsKey("link")) {
                Log.d(TAG, Objects.requireNonNull(message.getData().get("link")));
                // notificationBuilder.setContentIntent(createMessageIntent(context, title, body, imageUrl));
            } else {
                Log.d(TAG, new Gson().toJson(message.getData()));
            }


            // image is not null
            if (message.getNotification().getImageUrl() != null) {
                String imageUrl = message.getNotification().getImageUrl().toString();
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                    notificationBuilder.setLargeIcon(imageBitmap);
                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imageBitmap).bigLargeIcon(imageBitmap /* TODO:: null on error*/));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                notificationBuilder.setContentIntent(createMessageIntent(context, title, body, imageUrl));
            } else {
                // style
                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle(title);
                bigTextStyle.setSummaryText(channelId);
                bigTextStyle.bigText(body);

                notificationBuilder.setStyle(bigTextStyle);

                notificationBuilder.setContentIntent(noImgCreateIntent(context, title, body));
            }

            // Get the notification manager
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

        }
    }

    private static PendingIntent createMessageIntent(Context context, String title, String message, String imageUrl) {
        Intent intent = new Intent(context, NotificationShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("image", imageUrl);

        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private static PendingIntent noImgCreateIntent(Context context, String title, String message) {
        Intent intent = new Intent(context, NotificationShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("title", title);
        intent.putExtra("message", message);

        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }


    public static void createNotificationChannel(Context context, String channelId, String channelName, String channelDesc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelId.equals(context.getResources().getString(R.string.default_notification_channel_id)) ? NotificationManager.IMPORTANCE_DEFAULT : NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(channelDesc);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
