
package com.example.ads;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

public class AdManager {

    private static boolean initialized = false;

    public static final String BANNER_ID =
            "ca-app-pub-7584087725069099/8487278849";

    public static void initialize(Context context) {
        if (initialized) return;

        MobileAds.initialize(context.getApplicationContext(), status -> {
            initialized = true;
            Log.d("OmniToolsAds", "AdMob Initialized");
        });
    }

    public static AdView createBanner(Context context) {

        AdView adView = new AdView(context);

        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_ID);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.d("OmniToolsAds", "Banner Loaded Successfully");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError error) {
                Log.e("OmniToolsAds",
                        "Banner Error: " + error.getCode()
                        + " - " + error.getMessage());
            }
        });

        adView.loadAd(new AdRequest.Builder().build());

        return adView;
    }
}
