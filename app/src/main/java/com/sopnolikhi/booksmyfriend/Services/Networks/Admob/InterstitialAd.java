package com.sopnolikhi.booksmyfriend.Services.Networks.Admob;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAd {

    private static final long AD_SHOW_INTERVAL = 120000; // 2 minutes in milliseconds
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mImageAdsId;
    private static com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd;
    private static long lastAdShowTime = 0;
    private static CountDownTimer countDownTimer;

    public static void showAdIfAvailable(Context context, String imageAdsId) {
        mContext = context;
        mImageAdsId = imageAdsId;
        if (isAdAvailable()) {
            if (interstitialAd != null) {
                interstitialAd.show((Activity) context);
                lastAdShowTime = System.currentTimeMillis();
            } else {
                loadAd();
            }
        }
    }

    private static boolean isAdAvailable() {
        long elapsedTimeSinceLastAdShow = System.currentTimeMillis() - lastAdShowTime;
        return elapsedTimeSinceLastAdShow >= AD_SHOW_INTERVAL;
    }

    private static void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        com.google.android.gms.ads.interstitial.InterstitialAd.load(mContext, mImageAdsId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                InterstitialAd.interstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        InterstitialAd.interstitialAd = null;
                        startCountdownTimer();

                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                InterstitialAd.interstitialAd = null;
            }
        });
    }

    private static void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(AD_SHOW_INTERVAL, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Not used
            }

            @Override
            public void onFinish() {
                if (isAdAvailable()) {
                    if (interstitialAd != null) {
                        interstitialAd.show((Activity) mContext);
                        lastAdShowTime = System.currentTimeMillis();
                    } else {
                        loadAd();
                    }
                }
            }
        };
        countDownTimer.start();
    }
}
