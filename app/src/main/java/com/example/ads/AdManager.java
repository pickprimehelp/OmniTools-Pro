package com.example.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdManager.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\u000e\u0010)\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\u000e\u0010*\u001a\u00020&2\u0006\u0010'\u001a\u00020(J&\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020\u00072\f\u0010/\u001a\b\u0012\u0004\u0012\u00020&00J*\u00101\u001a\u00020&2\u0006\u0010,\u001a\u00020-2\f\u00102\u001a\b\u0012\u0004\u0012\u00020&002\f\u0010/\u001a\b\u0012\u0004\u0012\u00020&00R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0017\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020 X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\"X\u0082T¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/example/ads/AdManager;", "", "<init>", "()V", "TAG", "", "USE_TEST_ADS", "", "TEST_APP_ID", "TEST_BANNER_ID", "TEST_INTERSTITIAL_ID", "TEST_REWARDED_ID", "REAL_APP_ID", "REAL_BANNER_ID", "REAL_INTERSTITIAL_ID", "REAL_REWARDED_ID", "APP_ID", "getAPP_ID", "()Ljava/lang/String;", "BANNER_ID", "getBANNER_ID", "INTERSTITIAL_ID", "getINTERSTITIAL_ID", "REWARDED_ID", "getREWARDED_ID", "interstitialAd", "Lcom/google/android/gms/ads/interstitial/InterstitialAd;", "rewardedAd", "Lcom/google/android/gms/ads/rewarded/RewardedAd;", "isInterstitialLoading", "isRewardedLoading", "lastInterstitialShownTime", "", "screenChangeCount", "", "INTERSTITIAL_COOLDOWN_MS", "SCREENS_BETWEEN_ADS", "initialize", "", "context", "Landroid/content/Context;", "preloadInterstitial", "preloadRewarded", "showInterstitialIfReady", "activity", "Landroid/app/Activity;", "forceShow", "onDismissed", "Lkotlin/Function0;", "showRewardedAd", "onRewardEarned", "app"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class AdManager {
    private static final long INTERSTITIAL_COOLDOWN_MS = 30000;
    public static final String REAL_APP_ID = "ca-app-pub-7584087725069099~5449667520";
    public static final String REAL_BANNER_ID = "ca-app-pub-7584087725069099/8154712759";
    public static final String REAL_INTERSTITIAL_ID = "ca-app-pub-7584087725069099/1322545249";
    public static final String REAL_REWARDED_ID = "ca-app-pub-7584087725069099/3640752672";
    private static final int SCREENS_BETWEEN_ADS = 2;
    private static final String TAG = "OmniToolsAds";
    public static final String TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713";
    public static final String TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    public static final String TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";
    public static final String TEST_REWARDED_ID = "ca-app-pub-3940256099942544/5224354917";
    public static final boolean USE_TEST_ADS = false;
    private static InterstitialAd interstitialAd;
    private static boolean isInterstitialLoading;
    private static boolean isRewardedLoading;
    private static long lastInterstitialShownTime;
    private static RewardedAd rewardedAd;
    private static int screenChangeCount;
    public static final AdManager INSTANCE = new AdManager();
    public static final int $stable = 8;

    private AdManager() {
    }

    public final String getAPP_ID() {
        return REAL_APP_ID;
    }

    public final String getBANNER_ID() {
        return REAL_BANNER_ID;
    }

    public final String getINTERSTITIAL_ID() {
        return REAL_INTERSTITIAL_ID;
    }

    public final String getREWARDED_ID() {
        return REAL_REWARDED_ID;
    }

    public final void initialize(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            MobileAds.initialize(context, new OnInitializationCompleteListener() { // from class: com.example.ads.AdManager$$ExternalSyntheticLambda1
                @Override // com.google.android.gms.ads.initialization.OnInitializationCompleteListener
                public final void onInitializationComplete(InitializationStatus initializationStatus) {
                    AdManager.initialize$lambda$0(context, initializationStatus);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error initializing MobileAds", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void initialize$lambda$0(Context $context, InitializationStatus it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Log.d(TAG, "MobileAds initialized successfully");
        INSTANCE.preloadInterstitial($context);
        INSTANCE.preloadRewarded($context);
    }

    public final void preloadInterstitial(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (interstitialAd != null || isInterstitialLoading) {
            return;
        }
        isInterstitialLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        Intrinsics.checkNotNullExpressionValue(adRequest, "build(...)");
        InterstitialAd.load(context, getINTERSTITIAL_ID(), adRequest, new InterstitialAdLoadCallback() { // from class: com.example.ads.AdManager$preloadInterstitial$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(InterstitialAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                AdManager adManager = AdManager.INSTANCE;
                AdManager.interstitialAd = ad;
                AdManager adManager2 = AdManager.INSTANCE;
                AdManager.isInterstitialLoading = false;
                Log.d("OmniToolsAds", "Interstitial loaded");
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Intrinsics.checkNotNullParameter(loadAdError, "loadAdError");
                AdManager adManager = AdManager.INSTANCE;
                AdManager.interstitialAd = null;
                AdManager adManager2 = AdManager.INSTANCE;
                AdManager.isInterstitialLoading = false;
                Log.w("OmniToolsAds", "Interstitial failed to load: " + loadAdError.getMessage());
            }
        });
    }

    public final void preloadRewarded(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (rewardedAd != null || isRewardedLoading) {
            return;
        }
        isRewardedLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        Intrinsics.checkNotNullExpressionValue(adRequest, "build(...)");
        RewardedAd.load(context, getREWARDED_ID(), adRequest, new RewardedAdLoadCallback() { // from class: com.example.ads.AdManager$preloadRewarded$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(RewardedAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                AdManager adManager = AdManager.INSTANCE;
                AdManager.rewardedAd = ad;
                AdManager adManager2 = AdManager.INSTANCE;
                AdManager.isRewardedLoading = false;
                Log.d("OmniToolsAds", "Rewarded ad loaded");
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Intrinsics.checkNotNullParameter(loadAdError, "loadAdError");
                AdManager adManager = AdManager.INSTANCE;
                AdManager.rewardedAd = null;
                AdManager adManager2 = AdManager.INSTANCE;
                AdManager.isRewardedLoading = false;
                Log.w("OmniToolsAds", "Rewarded failed to load: " + loadAdError.getMessage());
            }
        });
    }

    public static /* synthetic */ void showInterstitialIfReady$default(AdManager adManager, Activity activity, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        adManager.showInterstitialIfReady(activity, z, function0);
    }

    public final void showInterstitialIfReady(final Activity activity, boolean forceShow, final Function0<Unit> onDismissed) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        screenChangeCount++;
        long now = System.currentTimeMillis();
        boolean cooldownPassed = now - lastInterstitialShownTime > 30000;
        boolean countReached = screenChangeCount >= 2;
        InterstitialAd ad = interstitialAd;
        if (ad != null && (forceShow || (cooldownPassed && countReached))) {
            ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.example.ads.AdManager$showInterstitialIfReady$1
                @Override // com.google.android.gms.ads.FullScreenContentCallback
                public void onAdDismissedFullScreenContent() {
                    AdManager adManager = AdManager.INSTANCE;
                    AdManager.interstitialAd = null;
                    AdManager adManager2 = AdManager.INSTANCE;
                    AdManager.lastInterstitialShownTime = System.currentTimeMillis();
                    AdManager adManager3 = AdManager.INSTANCE;
                    AdManager.screenChangeCount = 0;
                    AdManager.INSTANCE.preloadInterstitial(activity);
                    onDismissed.invoke();
                }

                @Override // com.google.android.gms.ads.FullScreenContentCallback
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Intrinsics.checkNotNullParameter(adError, "adError");
                    AdManager adManager = AdManager.INSTANCE;
                    AdManager.interstitialAd = null;
                    AdManager.INSTANCE.preloadInterstitial(activity);
                    onDismissed.invoke();
                }
            });
            ad.show(activity);
        } else {
            preloadInterstitial(activity);
            onDismissed.invoke();
        }
    }

    public final void showRewardedAd(final Activity activity, final Function0<Unit> onRewardEarned, final Function0<Unit> onDismissed) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onRewardEarned, "onRewardEarned");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        RewardedAd ad = rewardedAd;
        if (ad != null) {
            ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.example.ads.AdManager$showRewardedAd$1
                @Override // com.google.android.gms.ads.FullScreenContentCallback
                public void onAdDismissedFullScreenContent() {
                    AdManager adManager = AdManager.INSTANCE;
                    AdManager.rewardedAd = null;
                    AdManager.INSTANCE.preloadRewarded(activity);
                    onDismissed.invoke();
                }

                @Override // com.google.android.gms.ads.FullScreenContentCallback
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Intrinsics.checkNotNullParameter(adError, "adError");
                    AdManager adManager = AdManager.INSTANCE;
                    AdManager.rewardedAd = null;
                    AdManager.INSTANCE.preloadRewarded(activity);
                    onDismissed.invoke();
                }
            });
            ad.show(activity, new OnUserEarnedRewardListener() { // from class: com.example.ads.AdManager$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.ads.OnUserEarnedRewardListener
                public final void onUserEarnedReward(RewardItem rewardItem) {
                    AdManager.showRewardedAd$lambda$1(Function0.this, rewardItem);
                }
            });
        } else {
            preloadRewarded(activity);
            onRewardEarned.invoke();
            onDismissed.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void showRewardedAd$lambda$1(Function0 $onRewardEarned, RewardItem it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $onRewardEarned.invoke();
    }
}
