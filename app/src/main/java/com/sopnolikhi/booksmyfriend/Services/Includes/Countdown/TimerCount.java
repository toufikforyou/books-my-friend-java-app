package com.sopnolikhi.booksmyfriend.Services.Includes.Countdown;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;

import java.util.Locale;

public class TimerCount {
    private static TimerInterface listener;
    private static CountDownTimer countDownTimer;

    public static void startTimer(long sec, TimerInterface timerInterface) {
        listener = timerInterface;

        countDownTimer = new CountDownTimer((sec * 1000) - 1, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {

                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = millisUntilFinished / 1000 % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                if (listener != null) {
                    listener.onUpdateTime(timeLeftFormatted);
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onUpdateFinish();
                }
            }
        }.start();
    }

    public static void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
