package com.example.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.billing.SubscriptionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SubscriptionDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val context = LocalContext.current
        var selectedPlanIndex by remember { mutableIntStateOf(2) } // Default to 1 Year (Best Value)
        val isVip = SubscriptionManager.isVipActive()
        val plans = SubscriptionManager.ALL_PLANS
        val currentPlan = plans.getOrNull(selectedPlanIndex) ?: plans.last()

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF0F172A), // Luxury Dark Slate
            border = BorderStroke(1.5.dp, Color(0xFFF59E0B).copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Close & Status Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isVip) Color(0xFF059669) else Color(0xFFF59E0B).copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, if (isVip) Color(0xFF10B981) else Color(0xFFF59E0B))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                if (isVip) Icons.Filled.Check else Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (isVip) Color.White else Color(0xFFF59E0B),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                if (isVip) "VIP PRO ACTIVE" else "VIP UPGRADE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isVip) Color.White else Color(0xFFFDE68A)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Crown Icon & Hero Header
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFB45309))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.WorkspacePremium,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "OmniTools VIP Pro",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Upgrade for complete Ad-Free freedom & unlimited watermark-free downloads",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                Spacer(Modifier.height(16.dp))

                // Benefits Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    border = BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        VipBenefitItem(
                            icon = "🚫",
                            title = "100% Ad-Free Experience",
                            description = "No banner ads, no interstitial ads while switching tools"
                        )
                        VipBenefitItem(
                            icon = "💎",
                            title = "Unlimited Watermark-Free HD Exports",
                            description = "Direct 1-tap download without watching rewarded video ads"
                        )
                        VipBenefitItem(
                            icon = "⚡",
                            title = "High Resolution & Faster Processing",
                            description = "Crystal clear 1080p card, thumbnail & reel rendering"
                        )
                        VipBenefitItem(
                            icon = "👑",
                            title = "VIP Member Badge & Priority Support",
                            description = "Exclusive access to all current and future tools"
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                // If already VIP active, display current status and expiry
                if (isVip) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                        border = BorderStroke(1.5.dp, Color(0xFF10B981))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("✓ You Are a VIP Pro Member!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            val expiry = SubscriptionManager.expiryTimeMs
                            if (expiry > 0) {
                                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                                Text("Active until: ${sdf.format(Date(expiry))}", color = Color(0xFFA7F3D0), fontSize = 12.sp)
                            }
                            Text("Thank you for supporting OmniTools! All ads and watermarks are disabled.", color = Color.White.copy(alpha = 0.85f), fontSize = 11.5.sp, textAlign = TextAlign.Center)

                            Button(
                                onClick = { SubscriptionManager.openGooglePlaySubscriptions(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Manage Subscription on Google Play", fontSize = 12.sp)
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                Text(
                    text = "Select Subscription Plan",
                    color = Color(0xFFFDE68A),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(8.dp))

                // Pricing Cards (1 Month, 6 Months, 1 Year)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    plans.forEachIndexed { index, plan ->
                        val isSelected = selectedPlanIndex == index
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPlanIndex = index },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFF1E1B4B) else Color(0xFF1E293B)
                            ),
                            border = BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) Color(0xFFF59E0B) else Color(0xFF334155)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedPlanIndex = index },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color(0xFFF59E0B),
                                            unselectedColor = Color(0xFF64748B)
                                        )
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = plan.title,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                            if (plan.discountTag != null) {
                                                Spacer(Modifier.width(6.dp))
                                                Surface(
                                                    color = if (plan.isBestValue) Color(0xFFEF4444) else Color(0xFFD97706),
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text(
                                                        text = plan.discountTag,
                                                        color = Color.White,
                                                        fontSize = 9.5.sp,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = "${plan.durationLabel} access • ${plan.monthlyEquivalent}",
                                            color = Color(0xFF94A3B8),
                                            fontSize = 11.5.sp
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = plan.priceInr,
                                        color = if (isSelected) Color(0xFFFBBF24) else Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Text(
                                        text = plan.priceUsd,
                                        color = Color(0xFF64748B),
                                        fontSize = 10.5.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // Primary CTA Button
                Button(
                    onClick = {
                        SubscriptionManager.purchasePlan(context, currentPlan) {
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF59E0B)
                    )
                ) {
                    Icon(Icons.Filled.WorkspacePremium, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Upgrade to ${currentPlan.title} (${currentPlan.priceInr})",
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.5.sp
                    )
                }

                Spacer(Modifier.height(10.dp))

                // Secondary Actions: Restore & Google Play Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { SubscriptionManager.restorePurchases(context) }) {
                        Text("Restore Purchases", color = Color(0xFF38BDF8), fontSize = 11.5.sp)
                    }

                    TextButton(onClick = { SubscriptionManager.openGooglePlaySubscriptions(context) }) {
                        Text("Manage Subscriptions", color = Color(0xFF94A3B8), fontSize = 11.5.sp)
                    }
                }

                Text(
                    text = "Subscriptions auto-renew according to your chosen plan. You can cancel anytime easily from Google Play Store account settings at least 24 hours before the end of the current period.",
                    color = Color(0xFF64748B),
                    fontSize = 9.5.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun VipBenefitItem(
    icon: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = icon, fontSize = 18.sp)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(text = description, color = Color(0xFF94A3B8), fontSize = 11.sp, lineHeight = 15.sp)
        }
    }
}
