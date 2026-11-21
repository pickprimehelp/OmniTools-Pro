
package com.example.ads;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;

public class AdManager {

    private static boolean initialized = false;

    public static void initialize(Context context) {
        if (initialized) return;

        MobileAds.initialize(context.getApplicationContext(), status -> {
            initialized = true;
            Log.d("OmniToolsAds", "AdMob Initialized");
        });
    }
}

