package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdManager {
    private const val TAG = "OmniToolsAds"

    /**
     * =========================================================================
     * 🟢 ADMOB ADS CONFIGURATION
     * =========================================================================
     * Set USE_TEST_ADS = true  -> Tests ads safely with Google Official Test IDs
     * Set USE_TEST_ADS = false -> Shows your Real AdMob Ads for Play Store release
     */
    const val USE_TEST_ADS = true

    // --- 🧪 OFFICIAL GOOGLE TEST ADS IDS (Safe for testing, 100% fill rate) ---
    const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"
    const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
    const val TEST_REWARDED_ID = "ca-app-pub-3940256099942544/5224354917"

    // --- 💰 YOUR REAL ADMOB IDS (Replace with your actual AdMob IDs for production) ---
    const val REAL_APP_ID = "ca-app-pub-7584087725069099~5449667520"
    const val REAL_BANNER_ID = "ca-app-pub-7584087725069099/8154712759"
    const val REAL_INTERSTITIAL_ID = "ca-app-pub-7584087725069099/1322545249"
    const val REAL_REWARDED_ID = "ca-app-pub-7584087725069099/3640752672"

    // Active IDs automatically picked based on USE_TEST_ADS setting
    val APP_ID: String get() = if (USE_TEST_ADS) TEST_APP_ID else REAL_APP_ID
    val BANNER_ID: String get() = if (USE_TEST_ADS) TEST_BANNER_ID else REAL_BANNER_ID
    val INTERSTITIAL_ID: String get() = if (USE_TEST_ADS) TEST_INTERSTITIAL_ID else REAL_INTERSTITIAL_ID
    val REWARDED_ID: String get() = if (USE_TEST_ADS) TEST_REWARDED_ID else REAL_REWARDED_ID

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var isInterstitialLoading = false
    private var isRewardedLoading = false

    private var lastInterstitialShownTime: Long = 0
    private var screenChangeCount = 0
    private const val INTERSTITIAL_COOLDOWN_MS = 30_000L // 30s policy-compliant cooldown
    private const val SCREENS_BETWEEN_ADS = 2

    fun initialize(context: Context) {
        try {
            MobileAds.initialize(context) {
                Log.d(TAG, "MobileAds initialized successfully")
                preloadInterstitial(context)
                preloadRewarded(context)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MobileAds", e)
        }
    }

    fun preloadInterstitial(context: Context) {
        if (interstitialAd != null || isInterstitialLoading) return
        isInterstitialLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isInterstitialLoading = false
                    Log.d(TAG, "Interstitial loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isInterstitialLoading = false
                    Log.w(TAG, "Interstitial failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    fun preloadRewarded(context: Context) {
        if (rewardedAd != null || isRewardedLoading) return
        isRewardedLoading = true

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            REWARDED_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    isRewardedLoading = false
                    Log.d(TAG, "Rewarded ad loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedAd = null
                    isRewardedLoading = false
                    Log.w(TAG, "Rewarded failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Shows interstitial ad on tool change or action, respecting Google Play Ads policy.
     */
    fun showInterstitialIfReady(
        activity: Activity,
        forceShow: Boolean = false,
        onDismissed: () -> Unit
    ) {
        screenChangeCount++
        val now = System.currentTimeMillis()
        val cooldownPassed = (now - lastInterstitialShownTime) > INTERSTITIAL_COOLDOWN_MS
        val countReached = screenChangeCount >= SCREENS_BETWEEN_ADS

        val ad = interstitialAd
        if (ad != null && (forceShow || (cooldownPassed && countReached))) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    lastInterstitialShownTime = System.currentTimeMillis()
                    screenChangeCount = 0
                    preloadInterstitial(activity)
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    interstitialAd = null
                    preloadInterstitial(activity)
                    onDismissed()
                }
            }
            ad.show(activity)
        } else {
            // Ad not ready or on cooldown, proceed smoothly without blocking the user
            preloadInterstitial(activity)
            onDismissed()
        }
    }

    fun showRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit
    ) {
        val ad = rewardedAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    preloadRewarded(activity)
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    rewardedAd = null
                    preloadRewarded(activity)
                    onDismissed()
                }
            }
            ad.show(activity) {
                onRewardEarned()
            }
        } else {
            // If ad not loaded yet, grant the reward gracefully to avoid user frustration
            preloadRewarded(activity)
            onRewardEarned()
            onDismissed()
        }
    }
}

/**
 * Clean Top Banner Ad Composable placed at the very top of screens.
 * Google Play compliant: doesn't overlap tools or input fields.
 */
@Composable
fun TopBannerAd(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isAdFailed by remember { mutableStateOf(false) }

    if (isAdFailed) {
        // Subtle placeholder that keeps layout stable without jarring shift
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "OmniTools Pro • 40+ All-in-One Utilities",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    AdView(ctx).apply {
                        setAdSize(AdSize.BANNER)
                        adUnitId = AdManager.BANNER_ID
                        adListener = object : AdListener() {
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                Log.w("TopBannerAd", "Banner failed: ${error.message}")
                                isAdFailed = true
                            }
                            override fun onAdLoaded() {
                                isAdFailed = false
                            }
                        }
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )
        }
    }
}
