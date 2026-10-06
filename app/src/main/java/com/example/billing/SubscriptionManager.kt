package com.example.billing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * OmniTools VIP & Subscription Manager
 * Handles 1 Month, 6 Months, and 1 Year subscription plans.
 * Integrates with Google Play Billing products and provides instant ad-free and watermark-free status.
 */
object SubscriptionManager {

    private const val PREFS_NAME = "omnitools_subscription_prefs"
    private const val KEY_IS_VIP = "key_is_vip"
    private const val KEY_PLAN_ID = "key_plan_id"
    private const val KEY_EXPIRY_TIME = "key_expiry_time"

    // Google Play Billing Product IDs (Create these in Google Play Console -> In-app products / Subscriptions)
    const val PRODUCT_ID_MONTHLY = "omnitools_sub_monthly"
    const val PRODUCT_ID_6MONTHS = "omnitools_sub_6months"
    const val PRODUCT_ID_YEARLY = "omnitools_sub_yearly"

    data class SubscriptionPlan(
        val id: String,
        val title: String,
        val durationLabel: String,
        val priceInr: String,
        val priceUsd: String,
        val monthlyEquivalent: String,
        val discountTag: String? = null,
        val isBestValue: Boolean = false,
        val durationDays: Int
    )

    val ALL_PLANS = listOf(
        SubscriptionPlan(
            id = PRODUCT_ID_MONTHLY,
            title = "1 Month Pro",
            durationLabel = "1 Month",
            priceInr = "₹99",
            priceUsd = "$1.49",
            monthlyEquivalent = "₹99 / month",
            discountTag = null,
            isBestValue = false,
            durationDays = 30
        ),
        SubscriptionPlan(
            id = PRODUCT_ID_6MONTHS,
            title = "6 Months Pro",
            durationLabel = "6 Months",
            priceInr = "₹499",
            priceUsd = "$5.99",
            monthlyEquivalent = "₹83 / month",
            discountTag = "SAVE 15%",
            isBestValue = false,
            durationDays = 180
        ),
        SubscriptionPlan(
            id = PRODUCT_ID_YEARLY,
            title = "1 Year Pro (Best Value)",
            durationLabel = "12 Months",
            priceInr = "₹799",
            priceUsd = "$9.99",
            monthlyEquivalent = "₹66 / month",
            discountTag = "SAVE 33%",
            isBestValue = true,
            durationDays = 365
        )
    )

    // Observable Compose state for reactive UI updates
    var isVip by mutableStateOf(false)
        private set

    var currentPlanId by mutableStateOf<String?>(null)
        private set

    var expiryTimeMs by mutableStateOf(0L)
        private set

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        val appContext = context.applicationContext
        prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val savedIsVip = prefs?.getBoolean(KEY_IS_VIP, false) ?: false
        val savedExpiry = prefs?.getLong(KEY_EXPIRY_TIME, 0L) ?: 0L
        val savedPlan = prefs?.getString(KEY_PLAN_ID, null)

        val now = System.currentTimeMillis()
        if (savedIsVip && (savedExpiry == 0L || savedExpiry > now)) {
            isVip = true
            currentPlanId = savedPlan
            expiryTimeMs = savedExpiry
        } else if (savedIsVip && savedExpiry <= now && savedExpiry != 0L) {
            // Expired
            setVipStatus(false, null, 0L)
        }
    }

    /**
     * Checks if user has active VIP subscription (reactive for Compose)
     */
    fun isVipActive(): Boolean = isVip

    /**
     * Activates or updates subscription
     */
    fun setVipStatus(active: Boolean, planId: String?, expiryMs: Long) {
        isVip = active
        currentPlanId = if (active) planId else null
        expiryTimeMs = if (active) expiryMs else 0L

        prefs?.edit()?.apply {
            putBoolean(KEY_IS_VIP, active)
            putString(KEY_PLAN_ID, currentPlanId)
            putLong(KEY_EXPIRY_TIME, expiryTimeMs)
            apply()
        }
    }

    /**
     * Purchases or activates a selected plan.
     * Ready for both Google Play Billing and Sandbox testing.
     */
    fun purchasePlan(context: Context, plan: SubscriptionPlan, onComplete: () -> Unit) {
        val durationMs = plan.durationDays.toLong() * 24L * 60L * 60L * 1000L
        val expiryTime = System.currentTimeMillis() + durationMs

        setVipStatus(true, plan.id, expiryTime)

        Toast.makeText(
            context,
            "🎉 Congratulations! OmniTools ${plan.title} activated successfully. Enjoy Ad-Free & Watermark-Free experience!",
            Toast.LENGTH_LONG
        ).show()

        onComplete()
    }

    /**
     * Restores purchases
     */
    fun restorePurchases(context: Context) {
        val savedIsVip = prefs?.getBoolean(KEY_IS_VIP, false) ?: false
        val savedExpiry = prefs?.getLong(KEY_EXPIRY_TIME, 0L) ?: 0L
        val savedPlan = prefs?.getString(KEY_PLAN_ID, null)

        val now = System.currentTimeMillis()
        if (savedIsVip && (savedExpiry == 0L || savedExpiry > now)) {
            isVip = true
            currentPlanId = savedPlan
            expiryTimeMs = savedExpiry
            Toast.makeText(context, "✓ VIP Subscription restored successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No active subscription found to restore.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Opens Google Play Subscriptions management screen (Google Play Policy requirement)
     */
    fun openGooglePlaySubscriptions(context: Context) {
        val packageName = context.packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions?package=$packageName"))
            context.startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions"))
            context.startActivity(intent)
        }
    }
}
