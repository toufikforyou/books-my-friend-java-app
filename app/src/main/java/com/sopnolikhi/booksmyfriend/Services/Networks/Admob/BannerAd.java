package com.sopnolikhi.booksmyfriend.Services.Networks.Admob;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BannerAd {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static BannerAd instance;
    private final AdView adView;
    private static String mBannerAdsId;

    private BannerAd() {
        adView = new AdView(mContext);
        adView.setAdUnitId(mBannerAdsId);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
    }

    public static BannerAd getInstance(Context context, String bannerAdsId) {
        mContext = context;
        mBannerAdsId = bannerAdsId;
        if (instance == null) {
            instance = new BannerAd();
        }
        return instance;
    }

    public AdView getAdView() {
        return adView;
    }

}
