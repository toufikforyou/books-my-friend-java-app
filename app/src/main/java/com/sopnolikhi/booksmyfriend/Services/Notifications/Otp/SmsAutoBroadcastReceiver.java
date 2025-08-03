package com.sopnolikhi.booksmyfriend.Services.Notifications.Otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.Objects;

public class SmsAutoBroadcastReceiver extends BroadcastReceiver {
    SmsAutoReceiverListener smsAutoReceiverListener;

    public void intListener(SmsAutoReceiverListener smsAutoReceiverListener) {
        this.smsAutoReceiverListener = smsAutoReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();

            assert bundle != null;
            Status smsReceiverStatus = (Status) bundle.get(SmsRetriever.EXTRA_STATUS);

            if (smsReceiverStatus != null) {
                switch (Objects.requireNonNull(smsReceiverStatus).getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        if (smsAutoReceiverListener != null) {
                            smsAutoReceiverListener.onSuccessReadSms(bundle.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT));
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        if (smsAutoReceiverListener != null) {
                            smsAutoReceiverListener.onErrorReadSms();
                        }
                }
            }
        }

    }
}
