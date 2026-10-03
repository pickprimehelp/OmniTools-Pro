package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ads.AdManager
import com.example.ads.TopBannerAd
import com.example.model.ToolCategory
import com.example.model.ToolItem
import com.example.model.ToolRegistry
import com.example.util.ImageExportUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSelectTool: (String) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(ToolCategory.ALL) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var showRatingDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var userRating by remember { mutableIntStateOf(0) }

    val filteredTools = remember(searchQuery, selectedCategory) {
        ToolRegistry.allTools.filter { tool ->
            val matchesCategory = (selectedCategory == ToolCategory.ALL) || (tool.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                    tool.title.contains(searchQuery, ignoreCase = true) ||
                    tool.description.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    val featuredTools = remember {
        ToolRegistry.allTools.filter { it.isFeatured }
    }

    fun navigateWithAd(toolId: String) {
        if (activity != null) {
            AdManager.showInterstitialIfReady(activity) {
                onSelectTool(toolId)
            }
        } else {
            onSelectTool(toolId)
        }
    }

    fun openPlayStoreRating() {
        val packageName = context.packageName
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            context.startActivity(marketIntent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            context.startActivity(webIntent)
        }
    }

    fun shareApp() {
        val packageName = context.packageName
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "OmniTools Pro - 40+ All-in-One Tools")
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out OmniTools Pro! 40+ powerful all-in-one free tools for daily utility, designing, video creation, business & PDF:\nhttps://play.google.com/store/apps/details?id=$packageName"
            )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share OmniTools via"))
    }

    Scaffold(
        containerColor = Color(0xFFF1F5F9),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        if (isSearchVisible) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                placeholder = { Text("Search 40+ tools...", fontSize = 12.sp) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        searchQuery = ""
                                        isSearchVisible = false
                                    }) {
                                        Icon(Icons.Filled.Close, contentDescription = "Close Search", modifier = Modifier.size(18.dp))
                                    }
                                }
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "OmniTools",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.width(6.dp))
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        "PRO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        if (!isSearchVisible) {
                            IconButton(onClick = { isSearchVisible = true }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search Tools")
                            }
                            IconButton(onClick = { shareApp() }) {
                                Icon(Icons.Filled.Share, contentDescription = "Share App", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { showRatingDialog = true }) {
                                Icon(Icons.Filled.Star, contentDescription = "Rate App", tint = Color(0xFFF59E0B))
                            }
                            IconButton(onClick = { showPrivacyDialog = true }) {
                                Icon(Icons.Filled.Security, contentDescription = "Privacy Policy", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { showInfoDialog = true }) {
                                Icon(Icons.Filled.Info, contentDescription = "About App")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )

                // Banner ad sits cleanly below TopAppBar without overlapping status bar
                TopBannerAd()
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Welcome Banner (when not searching)
            if (searchQuery.isBlank() && selectedCategory == ToolCategory.ALL) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF4F46E5)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4338CA),
                                            Color(0xFF6366F1),
                                            Color(0xFF8B5CF6)
                                        )
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        color = Color.White.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            "ALL-IN-ONE SMART SUITE ✨",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        "40+ Super Tools Pro",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                    Spacer(Modifier.height(3.dp))
                                    Text(
                                        "Design cards, create invoices with GST, convert PDFs, change voice & calculate fast!",
                                        fontSize = 11.5.sp,
                                        color = Color.White.copy(alpha = 0.9f),
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Featured Tools Carousel (shown when not searching)
            if (searchQuery.isBlank() && selectedCategory == ToolCategory.ALL) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Popular Highlights", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Text("${featuredTools.size} Tools", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(featuredTools) { tool ->
                                FeaturedToolCard(tool = tool, onClick = { navigateWithAd(tool.id) })
                            }
                        }
                    }
                }
            }

            // Category Chips Row
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Categories",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ToolCategory.values().forEach { cat ->
                            val isSelected = selectedCategory == cat
                            val count = if (cat == ToolCategory.ALL) ToolRegistry.allTools.size
                            else ToolRegistry.allTools.count { it.category == cat }
                            Surface(
                                onClick = { selectedCategory = cat },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) cat.badgeColor else Color.White,
                                shadowElevation = if (isSelected) 3.dp else 1.dp,
                                border = if (!isSelected) BorderStroke(1.dp, Color(0xFFE2E8F0)) else null,
                                modifier = Modifier.height(36.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) Color.White else cat.badgeColor)
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = "${cat.title} ($count)",
                                        color = if (isSelected) Color.White else Color(0xFF334155),
                                        fontSize = 11.5.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tools Section Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (searchQuery.isNotBlank()) "Search Results (${filteredTools.size})"
                        else selectedCategory.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    if (searchQuery.isBlank()) {
                        Text(selectedCategory.subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Grid of Tools
            items(filteredTools.chunked(2)) { rowTools ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowTools.forEach { tool ->
                        Box(modifier = Modifier.weight(1f)) {
                            ToolCard(tool = tool, onClick = { navigateWithAd(tool.id) })
                        }
                    }
                    if (rowTools.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            // FOOTER SECTION: 1. Rate App & Feedback Card (100% Play Store Policy Compliant)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFEF3C7),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(24.dp))
                            }
                        }

                        Text("Enjoying OmniTools Pro?", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                        Text(
                            "Your honest rating and review helps us keep all 40+ tools completely free and brings new features. Tap below to share your feedback!",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Button(
                            onClick = { openPlayStoreRating() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Review on Google Play Store", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            // FOOTER SECTION: 2. Share OmniTools Card (100% Google Play Compliant)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }

                        Text(
                            "Share OmniTools with Friends & Family 🚀",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            "Love using our 40+ free utilities? Share OmniTools with your friends, family, and colleagues so everyone can create, calculate, and design easily!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Button(
                            onClick = { shareApp() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Share App Now", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }

    // Rate App Dialog (100% Google Play Developer Policy Compliant)
    if (showRatingDialog) {
        AlertDialog(
            onDismissRequest = { showRatingDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFF59E0B))
                    Spacer(Modifier.width(8.dp))
                    Text("Review on Play Store", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFEF3C7),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(28.dp))
                        }
                    }
                    Text(
                        "Enjoying OmniTools Pro?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        "Your honest rating and review helps us keep all 40+ tools completely free and regularly updated. Tap below to share your feedback!",
                        fontSize = 12.5.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRatingDialog = false
                        openPlayStoreRating()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Review on Google Play", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRatingDialog = false }) {
                    Text("Maybe Later")
                }
            }
        )
    }

    // App Info Dialog
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("About OmniTools")
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("OmniTools Pro - Version 1.0.0", fontWeight = FontWeight.Bold)
                    Text("An all-in-one suite of 40+ professional tools covering Wedding & Status Card Maker, Reels & YouTube Thumbnail Maker, Invoicing with GST, Voice Changer, Image & PDF processors, and daily utilities.")
                    Spacer(Modifier.height(4.dp))
                    Text("Watermark & PRO Plans:", fontWeight = FontWeight.SemiBold)
                    Text("Free exports include a subtle OmniTools watermark. Users can easily remove the watermark anytime by watching a short rewarded video ad!")
                    Spacer(Modifier.height(4.dp))
                    Text("Share & Support:", fontWeight = FontWeight.SemiBold)
                    Text("Love OmniTools? Share our app with friends and family to help support continuous free updates!")
                    Spacer(Modifier.height(4.dp))
                    Button(
                        onClick = {
                            showInfoDialog = false
                            shareApp()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Share OmniTools App")
                    }
                    Spacer(Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = {
                            showInfoDialog = false
                            showPrivacyDialog = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Read Privacy Policy")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Privacy Policy Dialog (Google Play Compliant)
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Privacy Policy & Data Safety", fontSize = 17.sp)
                }
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("OmniTools Privacy Commitment", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("1. 100% Local On-Device Processing: None of your photos, voice recordings, documents, invoices or designs are ever uploaded to any cloud server or database. All features run strictly on your device.")
                    Text("2. Microphone Permission (RECORD_AUDIO): Used strictly by the Voice Changer tool to record audio when you press record. Your recordings are processed locally and never transmitted.")
                    Text("3. Zero Personal Data Collection: We do not require registration, login, phone numbers, or passwords.")
                    Text("4. Google AdMob Advertising: We use Google AdMob to display banner, interstitial, and rewarded ads to keep the tools 100% free. AdMob may use standard Android Advertising ID according to Google's Privacy Policy.")
                    Text("5. Developer Contact: For questions or feedback, reach us at rambeerkashyap76@gmail.com.")
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun FeaturedToolCard(
    tool: ToolItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(225.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.5.dp, pressedElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0).copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Modern 3D Gradient Icon Badge
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .shadow(4.dp, RoundedCornerShape(14.dp), spotColor = tool.iconColor.copy(alpha = 0.5f))
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    tool.iconColor,
                                    tool.iconColor.copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(tool.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                }

                Surface(
                    color = tool.category.badgeColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "PRO",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = tool.category.badgeColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = tool.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = tool.description,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
fun ToolCard(
    tool: ToolItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0).copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Modern 3D Gradient Icon Badge
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(4.dp, RoundedCornerShape(13.dp), spotColor = tool.iconColor.copy(alpha = 0.5f))
                        .clip(RoundedCornerShape(13.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    tool.iconColor,
                                    tool.iconColor.copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(tool.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                }

                if (tool.isPopular) {
                    Surface(
                        color = Color(0xFFFF3366),
                        shape = RoundedCornerShape(8.dp),
                        shadowElevation = 1.dp
                    ) {
                        Text(
                            "HOT 🔥",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Text(
                text = tool.title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = tool.description,
                fontSize = 10.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )

            Surface(
                color = tool.category.badgeColor.copy(alpha = 0.08f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = tool.category.title,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = tool.category.badgeColor,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
            }
        }
    }
}
