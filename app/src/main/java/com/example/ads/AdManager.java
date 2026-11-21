
package com.example.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {

    private static final String TAG = "OmniToolsAds";

    public static final String BANNER_ID =
            "ca-app-pub-7584087725069099/8487278849";

    public static final String INTERSTITIAL_ID =
            "ca-app-pub-7584087725069099/7969559042";

    public static final String REWARDED_ID =
            "ca-app-pub-7584087725069099/3640752672";

    private static boolean initialized = false;

    private static InterstitialAd interstitialAd;
    private static RewardedAd rewardedAd;

    // INITIALIZE ADMOB
    public static synchronized void initialize(Context context) {

        if (initialized) return;

        MobileAds.initialize(
                context.getApplicationContext(),
                status -> {
                    initialized = true;
                    Log.d(TAG, "AdMob Initialized Successfully");

                    loadInterstitial(context);
                    loadRewarded(context);
                }
        );
    }

    // CREATE BANNER AD
    public static AdView createBanner(Context context) {

        AdView adView = new AdView(context);

        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_ID);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "Banner Loaded Successfully");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError error) {
                Log.e(TAG, "Banner Error: "
                        + error.getCode() + " - "
                        + error.getMessage());
            }
        });

        adView.loadAd(new AdRequest.Builder().build());

        return adView;
    }

    // LOAD INTERSTITIAL AD
    public static void loadInterstitial(Context context) {

        InterstitialAd.load(
                context.getApplicationContext(),
                INTERSTITIAL_ID,
                new AdRequest.Builder().build(),

                new InterstitialAdLoadCallback() {

                    @Override
                    public void onAdLoaded(InterstitialAd ad) {
                        interstitialAd = ad;
                        Log.d(TAG, "Interstitial Loaded");
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError error) {
                        interstitialAd = null;

                        Log.e(TAG, "Interstitial Error: "
                                + error.getCode() + " - "
                                + error.getMessage());
                    }
                }
        );
    }

    // SHOW INTERSTITIAL AD
    public static void showInterstitial(Activity activity) {

        if (interstitialAd == null) {
            Log.d(TAG, "Interstitial Not Ready");
            loadInterstitial(activity);
            return;
        }

        InterstitialAd ad = interstitialAd;
        interstitialAd = null;

        ad.setFullScreenContentCallback(
                new FullScreenContentCallback() {

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadInterstitial(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(
                            AdError error) {

                        Log.e(TAG, "Interstitial Show Error: "
                                + error.getMessage());

                        loadInterstitial(activity);
                    }
                }
        );

        ad.show(activity);
    }

    // LOAD REWARDED AD
    public static void loadRewarded(Context context) {

        RewardedAd.load(
                context.getApplicationContext(),
                REWARDED_ID,
                new AdRequest.Builder().build(),

                new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Rewarded Ad Loaded");
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError error) {
                        rewardedAd = null;

                        Log.e(TAG, "Rewarded Error: "
                                + error.getCode() + " - "
                                + error.getMessage());
                    }
                }
        );
    }

    // SHOW REWARDED AD
    public static void showRewarded(
            Activity activity,
            Runnable onRewardEarned) {

        if (rewardedAd == null) {
            Log.d(TAG, "Rewarded Ad Not Ready");
            loadRewarded(activity);
            return;
        }

        RewardedAd ad = rewardedAd;
        rewardedAd = null;

        ad.setFullScreenContentCallback(
                new FullScreenContentCallback() {

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadRewarded(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(
                            AdError error) {

                        Log.e(TAG, "Rewarded Show Error: "
                                + error.getMessage());

                        loadRewarded(activity);
                    }
                }
        );

        ad.show(activity, rewardItem -> {
            Log.d(TAG, "Reward Earned");
            onRewardEarned.run();
        });
    }
}
