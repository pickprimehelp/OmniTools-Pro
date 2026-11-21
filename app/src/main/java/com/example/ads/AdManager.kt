package com.example.ads

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdError
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

    const val APP_ID =
        "ca-app-pub-7584087725069099~5449667520"

    const val BANNER_ID =
        "ca-app-pub-7584087725069099/8487278849"

    const val INTERSTITIAL_ID =
        "ca-app-pub-7584087725069099/7969559042"

    const val REWARDED_ID =
        "ca-app-pub-7584087725069099/3640752672"

    private val handler = Handler(Looper.getMainLooper())

    private var appContext: Context? = null
    private var initialized = false
    private var initializing = false

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    private var interstitialLoading = false
    private var rewardedLoading = false

    private var interstitialRetries = 0
    private var rewardedRetries = 0

    private var lastInterstitialTime = 0L
    private var screenChangeCount = 0

    private const val INTERSTITIAL_COOLDOWN = 45000L
    private const val FORCED_COOLDOWN = 15000L
    private const val SCREENS_BETWEEN_ADS = 2

    private fun runOnMain(action: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action()
        } else {
            handler.post(action)
        }
    }

    fun initialize(context: Context) {
        val safeContext = context.applicationContext
        appContext = safeContext

        if (initialized || initializing) return

        initializing = true

        try {
            MobileAds.initialize(safeContext) {
                runOnMain {
                    initializing = false
                    initialized = true

                    Log.d(TAG, "AdMob initialized")

                    preloadInterstitial(safeContext)
                    preloadRewarded(safeContext)
                }
            }
        } catch (e: Exception) {
            initializing = false
            Log.e(TAG, "Initialization error", e)
        }
    }

    fun preloadInterstitial(context: Context) {
        val safeContext = context.applicationContext
        appContext = safeContext

        runOnMain {
            if (!initialized ||
                interstitialAd != null ||
                interstitialLoading
            ) return@runOnMain

            interstitialLoading = true

            try {
                InterstitialAd.load(
                    safeContext,
                    INTERSTITIAL_ID,
                    AdRequest.Builder().build(),
                    object : InterstitialAdLoadCallback() {

                        override fun onAdLoaded(ad: InterstitialAd) {
                            interstitialAd = ad
                            interstitialLoading = false
                            interstitialRetries = 0

                            Log.d(TAG, "Interstitial loaded")
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            interstitialAd = null
                            interstitialLoading = false

                            Log.e(TAG, "Interstitial error: ${error.message}")

                            retryInterstitial()
                        }
                    }
                )
            } catch (e: Exception) {
                interstitialLoading = false
                Log.e(TAG, "Interstitial exception", e)
                retryInterstitial()
            }
        }
    }

    private fun retryInterstitial() {
        if (interstitialRetries >= 4) return

        val delays = longArrayOf(6000L, 15000L, 35000L, 60000L)
        val delay = delays[interstitialRetries++]

        handler.postDelayed({
            appContext?.let { preloadInterstitial(it) }
        }, delay)
    }

    fun preloadRewarded(context: Context) {
        val safeContext = context.applicationContext
        appContext = safeContext

        runOnMain {
            if (!initialized ||
                rewardedAd != null ||
                rewardedLoading
            ) return@runOnMain

            rewardedLoading = true

            try {
                RewardedAd.load(
                    safeContext,
                    REWARDED_ID,
                    AdRequest.Builder().build(),
                    object : RewardedAdLoadCallback() {

                        override fun onAdLoaded(ad: RewardedAd) {
                            rewardedAd = ad
                            rewardedLoading = false
                            rewardedRetries = 0

                            Log.d(TAG, "Rewarded ad loaded")
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            rewardedAd = null
                            rewardedLoading = false

                            Log.e(TAG, "Rewarded error: ${error.message}")

                            retryRewarded()
                        }
                    }
                )
            } catch (e: Exception) {
                rewardedLoading = false
                Log.e(TAG, "Rewarded exception", e)
                retryRewarded()
            }
        }
    }

    private fun retryRewarded() {
        if (rewardedRetries >= 4) return

        val delays = longArrayOf(6000L, 15000L, 35000L, 60000L)
        val delay = delays[rewardedRetries++]

        handler.postDelayed({
            appContext?.let { preloadRewarded(it) }
        }, delay)
    }

    fun showInterstitialIfReady(
        activity: Activity,
        forceShow: Boolean = false,
        onDismissed: () -> Unit
    ) {
        runOnMain {
            screenChangeCount++

            val now = System.currentTimeMillis()
            val elapsed = now - lastInterstitialTime

            val shouldShow = if (forceShow) {
                lastInterstitialTime == 0L ||
                    elapsed >= FORCED_COOLDOWN
            } else {
                (lastInterstitialTime == 0L ||
                    elapsed >= INTERSTITIAL_COOLDOWN) &&
                    screenChangeCount >= SCREENS_BETWEEN_ADS
            }

            val ad = interstitialAd

            if (ad == null ||
                !shouldShow ||
                activity.isFinishing ||
                activity.isDestroyed
            ) {
                appContext?.let { preloadInterstitial(it) }
                onDismissed()
                return@runOnMain
            }

            interstitialAd = null

            ad.fullScreenContentCallback =
                object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        lastInterstitialTime = System.currentTimeMillis()
                        screenChangeCount = 0

                        appContext?.let { preloadInterstitial(it) }
                        onDismissed()
                    }

                    override fun onAdFailedToShowFullScreenContent(
                        error: AdError
                    ) {
                        Log.e(TAG, "Interstitial show error: ${error.message}")

                        appContext?.let { preloadInterstitial(it) }
                        onDismissed()
                    }
                }

            try {
                ad.show(activity)
            } catch (e: Exception) {
                Log.e(TAG, "Interstitial show exception", e)
                appContext?.let { preloadInterstitial(it) }
                onDismissed()
            }
        }
    }

    fun showRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit
    ) {
        runOnMain {
            val ad = rewardedAd

            if (ad == null ||
                activity.isFinishing ||
                activity.isDestroyed
            ) {
                appContext?.let { preloadRewarded(it) }
                onDismissed()
                return@runOnMain
            }

            rewardedAd = null

            var rewardEarned = false

            ad.fullScreenContentCallback =
                object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        appContext?.let { preloadRewarded(it) }
                        onDismissed()
                    }

                    override fun onAdFailedToShowFullScreenContent(
                        error: AdError
                    ) {
                        Log.e(TAG, "Rewarded show error: ${error.message}")
                        appContext?.let { preloadRewarded(it) }
                        onDismissed()
                    }
                }

            try {
                ad.show(activity) {
                    if (!rewardEarned) {
                        rewardEarned = true
                        onRewardEarned()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Rewarded exception", e)
                appContext?.let { preloadRewarded(it) }
                onDismissed()
            }
        }
    }
}

private class BannerAdHolder(context: Context) {

    val container = FrameLayout(context)
    private val adView = AdView(context)

    private val handler = Handler(Looper.getMainLooper())

    var hasLoaded = false
    var isLoading = false
    var isDisposed = false

    private var retryCount = 0
    private var pendingRetry: Runnable? = null

    init {
        container.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val density = context.resources.displayMetrics.density
            .takeIf { it > 0f } ?: 1f

        val widthDp = (
            context.resources.displayMetrics.widthPixels / density
        ).toInt().coerceAtLeast(320)

        val size = try {
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                context,
                widthDp
            )
        } catch (e: Exception) {
            Log.e("TopBannerAd", "Ad size error", e)
            AdSize.BANNER
        }

        adView.adUnitId = AdManager.BANNER_ID
        adView.setAdSize(size)

        adView.adListener = object : AdListener() {

            override fun onAdLoaded() {
                hasLoaded = true
                isLoading = false
                retryCount = 0

                pendingRetry?.let { handler.removeCallbacks(it) }
                pendingRetry = null

                container.visibility = android.view.View.VISIBLE

                Log.d("TopBannerAd", "Banner loaded")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                isLoading = false

                Log.e(
                    "TopBannerAd",
                    "Banner error ${error.code}: ${error.message}"
                )

                if (isDisposed || hasLoaded) return

                if (retryCount < 4) {
                    val delays = longArrayOf(
                        3000L, 7000L, 15000L, 30000L
                    )

                    val delay = delays[retryCount++]

                    val retry = Runnable {
                        pendingRetry = null
                        loadIfNeeded()
                    }

                    pendingRetry = retry
                    handler.postDelayed(retry, delay)
                } else {
                    container.visibility = android.view.View.GONE
                }
            }
        }

        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER_HORIZONTAL
        )

        container.addView(adView, params)
    }

    fun loadIfNeeded() {
        if (isDisposed || hasLoaded || isLoading) return

        isLoading = true
        container.visibility = android.view.View.VISIBLE

        try {
            adView.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
            isLoading = false
            Log.e("TopBannerAd", "Banner load exception", e)
        }
    }

    fun resume() {
        if (!isDisposed) {
            try {
                adView.resume()
            } catch (e: Exception) {
                Log.e("TopBannerAd", "Resume error", e)
            }
        }
    }

    fun dispose() {
        if (isDisposed) return

        isDisposed = true

        pendingRetry?.let { handler.removeCallbacks(it) }
        pendingRetry = null
        handler.removeCallbacksAndMessages(null)

        try {
            adView.pause()
            adView.destroy()
        } catch (e: Exception) {
            Log.e("TopBannerAd", "Dispose error", e)
        }
    }
}

@Composable
fun TopBannerAd(modifier: Modifier = Modifier) {

    val holder = remember {
        mutableHolder()
    }

    DisposableEffect(Unit) {
        holder.value?.resume()

        onDispose {
            holder.value?.dispose()
            holder.value = null
        }
    }

    AndroidView(
        modifier = modifier,

        factory = { context ->
            val banner = BannerAdHolder(context)
            holder.value = banner
            banner.loadIfNeeded()
            banner.container
        },

        update = {
            holder.value?.let { banner ->
                if (!banner.hasLoaded && !banner.isLoading) {
                    banner.loadIfNeeded()
                }
            }
        }
    )
}

private fun mutableHolder() =
    androidx.compose.runtime.mutableStateOf<BannerAdHolder?>(null)
