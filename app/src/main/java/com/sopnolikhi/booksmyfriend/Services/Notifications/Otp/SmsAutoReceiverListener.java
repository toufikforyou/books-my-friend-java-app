package com.sopnolikhi.booksmyfriend.Services.Notifications.Otp;

import android.content.Intent;

public interface SmsAutoReceiverListener {
    void onSuccessReadSms(Intent intent);
    void onErrorReadSms();
}
