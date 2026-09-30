package com.example.ui.tools.creator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.TopBannerAd
import com.example.util.ImageExportUtils

// ==========================================
// 1. YOUTUBE TITLE & SHORTS GENERATOR
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeTitleGeneratorScreen(isShortsMode: Boolean = false, onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var topic by remember { mutableStateOf("Weight Loss Diet") }
    var tone by remember { mutableStateOf("Viral / Click-worthy") }

    val tones = listOf("Viral / Click-worthy", "How-To / Educational", "Listicle / Numbers", "Curiosity / Secret")

    fun generateTitles(t: String, selectedTone: String): List<String> {
        val clean = t.ifBlank { "Video Topic" }
        return if (isShortsMode) {
            listOf(
                "Stop Doing This For $clean! ❌ #shorts",
                "Best $clean Hack in 60 Seconds! ⚡",
                "3 Secrets of $clean Nobody Tells You! 🤫",
                "How I Mastered $clean (Shocking Result) 😱",
                "Never Ignore This Rule in $clean! 🚨",
                "1 Minute $clean Guide That Actually Works! 🔥",
                "Why Everyone is Wrong About $clean 🤯",
                "Try This Simple $clean Routine Today! ✨"
            )
        } else {
            when (selectedTone) {
                "How-To / Educational" -> listOf(
                    "How to Master $clean (Complete Step-by-Step Guide)",
                    "$clean for Beginners: Everything You Need to Know",
                    "How to Do $clean the Right Way (Avoid Common Mistakes)",
                    "The Ultimate Guide to $clean in 2026",
                    "How I Learned $clean from Scratch in 14 Days"
                )
                "Listicle / Numbers" -> listOf(
                    "Top 7 $clean Rules You Must Follow in 2026",
                    "5 Mistakes People Make with $clean (And How to Fix Them)",
                    "10 Powerful Tips for $clean That Actually Work",
                    "3 Quick Hacks to 10x Your $clean Results",
                    "5 Best Tools for $clean Every Creator Needs"
                )
                "Curiosity / Secret" -> listOf(
                    "The Truth About $clean (What Nobody is Telling You)",
                    "Why Most People Fail at $clean (Secret Exposed)",
                    "I Tested $clean for 30 Days and This Happened...",
                    "Is $clean Actually Worth It? Honest Truth",
                    "The Dark Side of $clean Nobody Talks About"
                )
                else -> listOf(
                    "DO NOT Start $clean Until You Watch This!",
                    "This ONE Change in $clean Doubled My Results!",
                    "How to Get Crazy Good at $clean (Fast & Easy)",
                    "The Genius $clean Strategy That Works Every Time",
                    "Stop Wasting Time on $clean (Do THIS Instead)",
                    "I Wish I Knew This About $clean 5 Years Ago!"
                )
            }
        }
    }

    var titles by remember { mutableStateOf(generateTitles(topic, tone)) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(if (isShortsMode) "Shorts Title Generator" else "YouTube Title Generator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = topic,
                onValueChange = {
                    topic = it
                    titles = generateTitles(it, tone)
                },
                label = { Text("Video Topic or Keywords") },
                modifier = Modifier.fillMaxWidth()
            )

            if (!isShortsMode) {
                Text("Select Tone / Style", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tones.forEach { item ->
                        FilterChip(
                            selected = tone == item,
                            onClick = {
                                tone = item
                                titles = generateTitles(topic, item)
                            },
                            label = { Text(item) }
                        )
                    }
                }
            }

            Button(
                onClick = { titles = generateTitles(topic, tone) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Generate Fresh Titles")
            }

            Text("Generated Titles (${titles.size})", fontWeight = FontWeight.Bold)
            titles.forEach { titleText ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = titleText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { ImageExportUtils.copyToClipboard(context, titleText, "Title") }
                        ) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 2. HASHTAG & TAG GENERATOR
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashtagAndTagGeneratorScreen(isTagMode: Boolean = false, onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var keyword by remember { mutableStateOf("Tech & AI") }

    fun generateTags(kw: String): List<String> {
        val clean = kw.lowercase().replace(" ", "")
        return if (isTagMode) {
            listOf(
                kw, "$kw tutorial", "best $kw", "how to $kw", "$kw 2026",
                "$kw tips", "$kw tricks", "learn $kw", "$kw review",
                "$kw guide", "$kw online", "$kw free", "$kw course"
            )
        } else {
            listOf(
                "#$clean", "#${clean}viral", "#trending", "#reelsindia", "#fyp",
                "#explorepage", "#shorts", "#youtubeshorts", "#instagramreels",
                "#creator", "#daily${clean}", "#viralpost", "#best${clean}"
            )
        }
    }

    var tags by remember { mutableStateOf(generateTags(keyword)) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(if (isTagMode) "SEO Video Tag Generator" else "Hashtag Generator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = keyword,
                onValueChange = {
                    keyword = it
                    tags = generateTags(it)
                },
                label = { Text("Enter Niche or Keywords") },
                modifier = Modifier.fillMaxWidth()
            )

            val fullText = if (isTagMode) tags.joinToString(", ") else tags.joinToString(" ")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isTagMode) "Tags (Comma Separated)" else "Trending Hashtags", fontWeight = FontWeight.Bold)
                        Text("${fullText.length} characters", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(fullText, fontSize = 13.sp, lineHeight = 20.sp)

                    Button(
                        onClick = { ImageExportUtils.copyToClipboard(context, fullText, if (isTagMode) "Tags" else "Hashtags") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(if (isTagMode) "Copy All Video Tags" else "Copy All Hashtags")
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. YOUTUBE REVENUE & CPM CALCULATOR
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeRevenueCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var dailyViews by remember { mutableFloatStateOf(10000f) }
    var estimatedCpm by remember { mutableFloatStateOf(2.5f) } // in USD

    val dailyEarnings = (dailyViews / 1000f) * estimatedCpm
    val monthlyEarnings = dailyEarnings * 30f
    val yearlyEarnings = dailyEarnings * 365f

    val inrRate = 84.0 // Approx INR per USD

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("YouTube Revenue Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Big Monthly Income Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Estimated Monthly Earnings", color = Color(0xFF6EE7B7), fontSize = 13.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "$${String.format("%,.0f", monthlyEarnings)}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "≈ ₹${String.format("%,.0f", monthlyEarnings * inrRate)} INR",
                        fontSize = 14.sp,
                        color = Color(0xFFA7F3D0)
                    )
                }
            }

            // Daily Views Slider
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Daily Views:", fontWeight = FontWeight.Bold)
                        Text("${String.format("%,.0f", dailyViews)} views/day", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Slider(
                        value = dailyViews,
                        onValueChange = { dailyViews = it },
                        valueRange = 1000f..500000f
                    )
                }
            }

            // CPM Slider
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimated CPM ($):", fontWeight = FontWeight.Bold)
                        Text("$${String.format("%.1f", estimatedCpm)} / 1,000 views", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Slider(
                        value = estimatedCpm,
                        onValueChange = { estimatedCpm = it },
                        valueRange = 0.5f..15.0f
                    )
                }
            }

            // Full Projection Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Earnings Projection", fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Daily Income:")
                        Text("$${String.format("%.2f", dailyEarnings)} (₹${String.format("%.0f", dailyEarnings * inrRate)})", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Monthly Income:")
                        Text("$${String.format("%.2f", monthlyEarnings)} (₹${String.format("%.0f", monthlyEarnings * inrRate)})", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Yearly Income:")
                        Text("$${String.format("%.2f", yearlyEarnings)} (₹${String.format("%.0f", yearlyEarnings * inrRate)})", fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. VIDEO FILE SIZE & THUMBNAIL SIZE TOOLS
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoFileSizeCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var durationMinutesText by remember { mutableStateOf("10") }
    var bitrateMbpsText by remember { mutableStateOf("16") } // 16 Mbps for 1080p 60fps

    val minutes = durationMinutesText.toDoubleOrNull() ?: 10.0
    val bitrateMbps = bitrateMbpsText.toDoubleOrNull() ?: 16.0

    // Video size = (bitrate in Mbps * seconds) / 8 = MB
    val totalSeconds = minutes * 60.0
    val sizeInMb = (bitrateMbps * totalSeconds) / 8.0
    val sizeInGb = sizeInMb / 1024.0

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Video File Size Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Estimated Video Size", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (sizeInGb >= 1.0) "${String.format("%.2f", sizeInGb)} GB" else "${String.format("%.0f", sizeInMb)} MB",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            OutlinedTextField(
                value = durationMinutesText,
                onValueChange = { durationMinutesText = it },
                label = { Text("Video Duration (Minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bitrateMbpsText,
                onValueChange = { bitrateMbpsText = it },
                label = { Text("Video Bitrate (Mbps)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Standard Bitrate Presets", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("1080p 30fps (8 Mbps)" to "8", "1080p 60fps (16 Mbps)" to "16", "4K 60fps (45 Mbps)" to "45").forEach { (label, rate) ->
                    FilterChip(
                        selected = bitrateMbpsText == rate,
                        onClick = { bitrateMbpsText = rate },
                        label = { Text(label.split(" ")[0]) }
                    )
                }
            }
        }
    }
}
