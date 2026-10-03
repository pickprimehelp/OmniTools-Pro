package com.example.ui.tools.design

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdManager
import com.example.ads.TopBannerAd
import com.example.util.ImageExportUtils

// Helper Composable for Watermark notice & 1-time Rewarded Video Ad unlock
@Composable
fun WatermarkControlBar(
    hasWatermark: Boolean,
    onWatchAdToSaveClean: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (hasWatermark) MaterialTheme.colorScheme.surfaceVariant else Color(0xFF059669).copy(alpha = 0.15f)
        ),
        border = BorderStroke(
            1.dp,
            if (hasWatermark) MaterialTheme.colorScheme.outline.copy(alpha = 0.3f) else Color(0xFF059669)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = if (hasWatermark) Icons.Filled.Lock else Icons.Filled.LockOpen,
                    contentDescription = null,
                    tint = if (hasWatermark) MaterialTheme.colorScheme.primary else Color(0xFF059669),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (hasWatermark) "Watermark: ON (Free)" else "Watermark: OFF (This Image)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (hasWatermark) MaterialTheme.colorScheme.onSurfaceVariant else Color(0xFF059669)
                    )
                    Text(
                        text = if (hasWatermark) "Watch 1 short video ad to remove watermark on this export" else "Clean watermark-free export ready! (Next image will need ad again)",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }
            if (hasWatermark) {
                Button(
                    onClick = onWatchAdToSaveClean,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Remove 🎁", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// 1. WEDDING CARD MAKER
// ==========================================

data class WeddingTheme(
    val name: String,
    val bgColors: List<Color>,
    val textColor: Color,
    val accentColor: Color,
    val borderColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeddingCardMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val themes = listOf(
        WeddingTheme("Royal Crimson", listOf(Color(0xFF4A0E17), Color(0xFF7A1C28)), Color(0xFFFFF1C5), Color(0xFFFFD700), Color(0xFFD4AF37)),
        WeddingTheme("Midnight Gold", listOf(Color(0xFF0F172A), Color(0xFF1E293B)), Color(0xFFFDE68A), Color(0xFFFBBF24), Color(0xFFF59E0B)),
        WeddingTheme("Emerald Palace", listOf(Color(0xFF064E3B), Color(0xFF047857)), Color(0xFFECFDF5), Color(0xFF6EE7B7), Color(0xFF34D399)),
        WeddingTheme("Pastel Rose", listOf(Color(0xFF831843), Color(0xFFBE185D)), Color(0xFFFFF1F2), Color(0xFFFDA4AF), Color(0xFFFB7185)),
        WeddingTheme("Classic Ivory", listOf(Color(0xFF332924), Color(0xFF4E3D35)), Color(0xFFFFFBEB), Color(0xFFE5C378), Color(0xFFD97706))
    )

    var selectedThemeIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var brideName by remember { mutableStateOf("Ananya Sharma") }
    var groomName by remember { mutableStateOf("Rohan Verma") }
    var weddingDate by remember { mutableStateOf("Sunday, 18 December 2026") }
    var weddingTime by remember { mutableStateOf("7:00 PM Onwards") }
    var venue by remember { mutableStateOf("The Grand Heritage Palace, Jaipur, Rajasthan") }
    var rsvp by remember { mutableStateOf("Sharma & Verma Family | +91 98765 43210") }
    var tagline by remember { mutableStateOf("Together with our families, we joyfully invite you to celebrate our union") }
    var hasWatermark by remember { mutableStateOf(true) }

    // Bride & Groom Photo Pickers
    var bridePhotoUri by remember { mutableStateOf<Uri?>(null) }
    var groomPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var brideBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var groomBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val bridePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            bridePhotoUri = uri
            try {
                val stream = context.contentResolver.openInputStream(uri)
                brideBitmap = BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading bride photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val groomPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            groomPhotoUri = uri
            try {
                val stream = context.contentResolver.openInputStream(uri)
                groomBitmap = BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading groom photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val activeTheme = themes[selectedThemeIndex]

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    // Save clean image without watermark for this specific image
                    val cleanBmp = renderWeddingCardBitmap(brideName, groomName, weddingDate, weddingTime, venue, rsvp, tagline, activeTheme, hasWatermark = false, brideBitmap, groomBitmap)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "WeddingCard_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next card will require watching video ad again.", Toast.LENGTH_LONG).show()
                    // Reset single-use watermark for subsequent generations
                    hasWatermark = true
                },
                onDismissed = {
                    hasWatermark = true
                }
            )
        } else {
            val cleanBmp = renderWeddingCardBitmap(brideName, groomName, weddingDate, weddingTime, venue, rsvp, tagline, activeTheme, hasWatermark = false, brideBitmap, groomBitmap)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "WeddingCard_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Wedding Card Maker") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Names & Info", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Couple Photos", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Themes", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("Preview & Save", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: Names & Details (Placed at Top so Keyboard Never Covers Inputs)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Quick Mini Preview Bar
                        Surface(
                            color = activeTheme.bgColors.first().copy(alpha = 0.9f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, activeTheme.borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTab = 3 }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("$brideName 💍 $groomName", color = activeTheme.textColor, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
                                    Text("Tap to view full live card preview ➔", color = activeTheme.accentColor, fontSize = 11.sp)
                                }
                                Icon(Icons.Filled.Visibility, contentDescription = null, tint = activeTheme.accentColor, modifier = Modifier.size(18.dp))
                            }
                        }

                        Text("Edit Wedding Invitation Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = brideName,
                            onValueChange = { brideName = it; hasWatermark = true },
                            label = { Text("Bride's Full Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = groomName,
                            onValueChange = { groomName = it; hasWatermark = true },
                            label = { Text("Groom's Full Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = weddingDate,
                            onValueChange = { weddingDate = it; hasWatermark = true },
                            label = { Text("Wedding Date") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = weddingTime,
                            onValueChange = { weddingTime = it; hasWatermark = true },
                            label = { Text("Wedding Time / Muhurat") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = venue,
                            onValueChange = { venue = it; hasWatermark = true },
                            label = { Text("Venue / Location Address") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = rsvp,
                            onValueChange = { rsvp = it; hasWatermark = true },
                            label = { Text("RSVP / Contact Info") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tagline,
                            onValueChange = { tagline = it; hasWatermark = true },
                            label = { Text("Tagline / Welcome Message") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Card Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                1 -> {
                    // TAB 1: Bride & Groom Photos
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("Add Photos on Card 📸 (Optional)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                                Text(
                                    "Upload Bride & Groom photos from your gallery to show in royal golden frames on your invitation card!",
                                    fontSize = 11.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Bride Photo Selector Card
                                    OutlinedCard(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                bridePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                            },
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.5.dp, if (brideBitmap != null) Color(0xFF059669) else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            if (brideBitmap != null) {
                                                Image(
                                                    bitmap = brideBitmap!!.asImageBitmap(),
                                                    contentDescription = "Bride Photo",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape)
                                                        .border(2.dp, Color(0xFF059669), CircleShape)
                                                )
                                                Text("Bride Added ✓", fontSize = 11.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)
                                                TextButton(
                                                    onClick = {
                                                        brideBitmap = null
                                                        bridePhotoUri = null
                                                    },
                                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                                                ) {
                                                    Text("Remove", fontSize = 10.5.sp, color = Color(0xFFEF4444))
                                                }
                                            } else {
                                                Surface(
                                                    shape = CircleShape,
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    modifier = Modifier.size(50.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                                    }
                                                }
                                                Text("Bride Photo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                                Text("Tap to upload", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            }
                                        }
                                    }

                                    // Groom Photo Selector Card
                                    OutlinedCard(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                groomPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                            },
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.5.dp, if (groomBitmap != null) Color(0xFF059669) else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            if (groomBitmap != null) {
                                                Image(
                                                    bitmap = groomBitmap!!.asImageBitmap(),
                                                    contentDescription = "Groom Photo",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape)
                                                        .border(2.dp, Color(0xFF059669), CircleShape)
                                                )
                                                Text("Groom Added ✓", fontSize = 11.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)
                                                TextButton(
                                                    onClick = {
                                                        groomBitmap = null
                                                        groomPhotoUri = null
                                                    },
                                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                                                ) {
                                                    Text("Remove", fontSize = 10.5.sp, color = Color(0xFFEF4444))
                                                }
                                            } else {
                                                Surface(
                                                    shape = CircleShape,
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    modifier = Modifier.size(50.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                                    }
                                                }
                                                Text("Groom Photo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                                Text("Tap to upload", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Card Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                2 -> {
                    // TAB 2: Themes & Design
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text("Select Card Theme", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        themes.forEachIndexed { index, theme ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedThemeIndex = index
                                        hasWatermark = true
                                    },
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(
                                    if (selectedThemeIndex == index) 2.5.dp else 1.dp,
                                    if (selectedThemeIndex == index) theme.accentColor else Color(0xFFE2E8F0)
                                ),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(Brush.linearGradient(theme.bgColors))
                                                .border(2.dp, theme.borderColor, CircleShape)
                                        )
                                        Spacer(Modifier.width(12.dp))
                                        Column {
                                            Text(theme.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                            Text("Royal traditional wedding palette", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                    if (selectedThemeIndex == index) {
                                        Surface(
                                            shape = CircleShape,
                                            color = theme.accentColor.copy(alpha = 0.2f),
                                            border = BorderStroke(1.dp, theme.accentColor)
                                        ) {
                                            Text(
                                                "Active ✓",
                                                color = theme.accentColor,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Card Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                3 -> {
                    // TAB 3: Full Live Preview & HD Export
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Live Card Preview
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.72f)
                                .shadow(12.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(3.dp, activeTheme.borderColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(activeTheme.bgColors))
                                    .padding(16.dp)
                            ) {
                                // Decorative inner border
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .border(1.dp, activeTheme.borderColor.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                                        .padding(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        // Top Ornament
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text("॥ श्री गणेशाय नमः ॥", color = activeTheme.accentColor, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(tagline, color = activeTheme.textColor.copy(alpha = 0.85f), fontSize = 10.5.sp, textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)
                                        }

                                        // Couple Photos Preview if added
                                        if (brideBitmap != null || groomBitmap != null) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                                horizontalArrangement = Arrangement.SpaceEvenly,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                // Bride photo
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(54.dp)
                                                            .clip(CircleShape)
                                                            .border(2.dp, activeTheme.borderColor, CircleShape)
                                                            .background(Color.Black.copy(alpha = 0.25f)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        if (brideBitmap != null) {
                                                            Image(
                                                                bitmap = brideBitmap!!.asImageBitmap(),
                                                                contentDescription = "Bride",
                                                                contentScale = ContentScale.Crop,
                                                                modifier = Modifier.fillMaxSize()
                                                            )
                                                        } else {
                                                            Icon(Icons.Filled.Person, contentDescription = null, tint = activeTheme.accentColor.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
                                                        }
                                                    }
                                                    Spacer(Modifier.height(2.dp))
                                                    Text("Bride", fontSize = 9.sp, color = activeTheme.accentColor, fontWeight = FontWeight.SemiBold)
                                                }

                                                Icon(
                                                    Icons.Filled.Favorite,
                                                    contentDescription = null,
                                                    tint = activeTheme.accentColor,
                                                    modifier = Modifier.size(18.dp)
                                                )

                                                // Groom photo
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(54.dp)
                                                            .clip(CircleShape)
                                                            .border(2.dp, activeTheme.borderColor, CircleShape)
                                                            .background(Color.Black.copy(alpha = 0.25f)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        if (groomBitmap != null) {
                                                            Image(
                                                                bitmap = groomBitmap!!.asImageBitmap(),
                                                                contentDescription = "Groom",
                                                                contentScale = ContentScale.Crop,
                                                                modifier = Modifier.fillMaxSize()
                                                            )
                                                        } else {
                                                            Icon(Icons.Filled.Person, contentDescription = null, tint = activeTheme.accentColor.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
                                                        }
                                                    }
                                                    Spacer(Modifier.height(2.dp))
                                                    Text("Groom", fontSize = 9.sp, color = activeTheme.accentColor, fontWeight = FontWeight.SemiBold)
                                                }
                                            }
                                        }

                                        // Center Couple Names
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(brideName, color = activeTheme.textColor, fontSize = if (brideBitmap != null || groomBitmap != null) 18.sp else 22.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                                            Text("&", color = activeTheme.accentColor, fontSize = if (brideBitmap != null || groomBitmap != null) 15.sp else 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(vertical = 1.dp))
                                            Text(groomName, color = activeTheme.textColor, fontSize = if (brideBitmap != null || groomBitmap != null) 18.sp else 22.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                                        }

                                        // Bottom Details
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text("🗓  $weddingDate", color = activeTheme.accentColor, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                                            Text("⏰  $weddingTime", color = activeTheme.textColor.copy(alpha = 0.9f), fontSize = 10.5.sp)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text("📍  $venue", color = activeTheme.textColor, fontSize = 10.5.sp, textAlign = TextAlign.Center, maxLines = 2)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("RSVP: $rsvp", color = activeTheme.accentColor, fontSize = 9.5.sp, fontWeight = FontWeight.Medium)

                                            if (hasWatermark) {
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Surface(
                                                    color = Color.Black.copy(alpha = 0.6f),
                                                    shape = RoundedCornerShape(8.dp)
                                                ) {
                                                    Text(
                                                        "⚡ Created with OmniTools App",
                                                        color = Color.White,
                                                        fontSize = 8.5.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Watermark Notice & Unlock Bar
                        WatermarkControlBar(
                            hasWatermark = hasWatermark,
                            onWatchAdToSaveClean = { saveWithoutWatermarkViaAd() }
                        )

                        // Primary Save Action: Remove Watermark & Save (Rewarded Video Ad)
                        Button(
                            onClick = { saveWithoutWatermarkViaAd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Remove Watermark & Save HD (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        // Regular Free Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderWeddingCardBitmap(brideName, groomName, weddingDate, weddingTime, venue, rsvp, tagline, activeTheme, hasWatermark = true, brideBitmap, groomBitmap)
                                    ImageExportUtils.saveBitmapToGallery(context, bmp, "WeddingCard_Free")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save (Free)")
                            }
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderWeddingCardBitmap(brideName, groomName, weddingDate, weddingTime, venue, rsvp, tagline, activeTheme, hasWatermark = true, brideBitmap, groomBitmap)
                                    ImageExportUtils.shareBitmap(context, bmp, "Wedding Invitation - $brideName & $groomName")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share Card")
                            }
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
            }
        }
    }
}

private fun drawCircularBitmap(
    canvas: Canvas,
    source: Bitmap,
    centerX: Float,
    centerY: Float,
    radius: Float,
    borderColor: Int,
    borderWidth: Float
) {
    try {
        val size = (radius * 2).toInt()
        if (size <= 0) return
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(output)
        val paint = Paint().apply { isAntiAlias = true }
        tempCanvas.drawCircle(radius, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val minDim = Math.min(source.width, source.height)
        val srcX = (source.width - minDim) / 2
        val srcY = (source.height - minDim) / 2
        val srcRect = Rect(srcX, srcY, srcX + minDim, srcY + minDim)
        val dstRect = Rect(0, 0, size, size)
        tempCanvas.drawBitmap(source, srcRect, dstRect, paint)

        // Draw clipped circle on destination canvas
        canvas.drawBitmap(output, centerX - radius, centerY - radius, null)

        // Draw royal gold border
        val borderPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = borderColor
            this.strokeWidth = borderWidth
        }
        canvas.drawCircle(centerX, centerY, radius, borderPaint)
    } catch (e: Exception) {
        // Fallback safely
    }
}

private fun renderWeddingCardBitmap(
    bride: String,
    groom: String,
    date: String,
    time: String,
    venue: String,
    rsvp: String,
    tagline: String,
    theme: WeddingTheme,
    hasWatermark: Boolean,
    brideBitmap: Bitmap? = null,
    groomBitmap: Bitmap? = null
): Bitmap {
    val width = 1080
    val height = 1440
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Background
    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            theme.bgColors.first().toArgb(),
            theme.bgColors.last().toArgb(),
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Outer Border
    val borderPaint = Paint().apply {
        color = theme.borderColor.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 14f
    }
    canvas.drawRoundRect(RectF(40f, 40f, width - 40f, height - 40f), 40f, 40f, borderPaint)

    // Inner subtle border
    val innerBorderPaint = Paint().apply {
        color = theme.borderColor.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 3f
        alpha = 150
    }
    canvas.drawRoundRect(RectF(70f, 70f, width - 70f, height - 70f), 24f, 24f, innerBorderPaint)

    val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    // Top Greeting
    textPaint.color = theme.accentColor.toArgb()
    textPaint.textSize = 38f
    textPaint.isFakeBoldText = true
    canvas.drawText("॥ श्री गणेशाय नमः ॥", width / 2f, 170f, textPaint)

    // Tagline
    textPaint.color = theme.textColor.toArgb()
    textPaint.textSize = 28f
    textPaint.isFakeBoldText = false
    canvas.drawText(tagline, width / 2f, 240f, textPaint)

    val hasPhotos = (brideBitmap != null || groomBitmap != null)

    // Draw Bride & Groom Photos if present
    if (hasPhotos) {
        val photoY = 410f
        val photoRadius = 110f
        if (brideBitmap != null && groomBitmap != null) {
            // Draw Bride Photo
            drawCircularBitmap(canvas, brideBitmap, width * 0.32f, photoY, photoRadius, theme.borderColor.toArgb(), 8f)
            // Draw Groom Photo
            drawCircularBitmap(canvas, groomBitmap, width * 0.68f, photoY, photoRadius, theme.borderColor.toArgb(), 8f)

            // Draw decorative & between photos
            val heartPaint = Paint().apply {
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
                textSize = 50f
                color = theme.accentColor.toArgb()
            }
            canvas.drawText("❤", width / 2f, photoY + 18f, heartPaint)
        } else if (brideBitmap != null) {
            drawCircularBitmap(canvas, brideBitmap, width / 2f, photoY, 130f, theme.borderColor.toArgb(), 10f)
        } else if (groomBitmap != null) {
            drawCircularBitmap(canvas, groomBitmap, width / 2f, photoY, 130f, theme.borderColor.toArgb(), 10f)
        }
    }

    // Couple Names
    val namesStartY = if (hasPhotos) 630f else 540f
    textPaint.textSize = if (hasPhotos) 64f else 72f
    textPaint.isFakeBoldText = true
    textPaint.color = theme.textColor.toArgb()
    canvas.drawText(bride, width / 2f, namesStartY, textPaint)

    textPaint.textSize = if (hasPhotos) 48f else 54f
    textPaint.color = theme.accentColor.toArgb()
    canvas.drawText("&", width / 2f, namesStartY + 80f, textPaint)

    textPaint.textSize = if (hasPhotos) 64f else 72f
    textPaint.color = theme.textColor.toArgb()
    canvas.drawText(groom, width / 2f, namesStartY + 160f, textPaint)

    // Date & Time
    val dateStartY = if (hasPhotos) 980f else 960f
    textPaint.textSize = 38f
    textPaint.color = theme.accentColor.toArgb()
    canvas.drawText("🗓  $date", width / 2f, dateStartY, textPaint)

    textPaint.textSize = 34f
    textPaint.color = theme.textColor.toArgb()
    canvas.drawText("⏰  $time", width / 2f, dateStartY + 65f, textPaint)

    // Venue
    textPaint.textSize = 32f
    canvas.drawText("📍  $venue", width / 2f, dateStartY + 165f, textPaint)

    // RSVP
    textPaint.textSize = 30f
    textPaint.color = theme.accentColor.toArgb()
    canvas.drawText("RSVP: $rsvp", width / 2f, dateStartY + 270f, textPaint)

    // Watermark if active
    if (hasWatermark) {
        val wmBg = Paint().apply {
            color = android.graphics.Color.argb(160, 0, 0, 0)
        }
        val wmText = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 28f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(width / 2f - 240f, height - 80f, width / 2f + 240f, height - 30f), 16f, 16f, wmBg)
        canvas.drawText("⚡ Created with OmniTools App", width / 2f, height - 44f, wmText)
    }

    return bitmap
}

// ==========================================
// 2. STATUS MAKER SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val quotePresets = listOf(
        "\"The secret of getting ahead is getting started.\"" to "Mark Twain",
        "\"जिंदगी आसान नहीं होती, इसे आसान बनाना पड़ता है। कुछ अंदाज से, कुछ नजरअंदाज से।\"" to "Life Motivation",
        "\"Your time is limited, so don't waste it living someone else's life.\"" to "Steve Jobs",
        "\"मेहनत इतनी खामोशी से करो कि सफलता शोर मचा दे।\"" to "Success Mantra",
        "\"Believe in yourself and you will be unstoppable.\"" to "Daily Spark",
        "\"Dream big, work hard, stay humble.\"" to "Inspiration"
    )

    var currentQuote by remember { mutableStateOf(quotePresets[0].first) }
    var currentAuthor by remember { mutableStateOf(quotePresets[0].second) }
    var selectedGradientIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var fontSizeSp by remember { mutableFloatStateOf(20f) }
    var hasWatermark by remember { mutableStateOf(true) }

    // Custom Photo Upload for Status
    var bgPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var bgPhotoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val statusPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            bgPhotoUri = uri
            try {
                val stream = context.contentResolver.openInputStream(uri)
                bgPhotoBitmap = BitmapFactory.decodeStream(stream)
                hasWatermark = true
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading background photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val gradients = listOf(
        listOf(Color(0xFF6366F1), Color(0xFFA855F7), Color(0xFFEC4899)),
        listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF334155)),
        listOf(Color(0xFF059669), Color(0xFF10B981), Color(0xFF06B6D4)),
        listOf(Color(0xFFF97316), Color(0xFFEC4899), Color(0xFF8B5CF6)),
        listOf(Color(0xFF1E1B4B), Color(0xFF312E81), Color(0xFF4338CA))
    )

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = false, bgPhotoBitmap = bgPhotoBitmap)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Status_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next image will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = false, bgPhotoBitmap = bgPhotoBitmap)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Status_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Status Maker Tools") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Quote & Text", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Add Photo 📸", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Backgrounds", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("Preview & Save", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: Quote & Text (Top-placed so soft keyboard never hides typing)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Quick Mini Preview
                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFF334155)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTab = 3 }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = currentQuote.take(40) + if (currentQuote.length > 40) "..." else "",
                                        color = Color.White,
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("— $currentAuthor • Tap to view live status ➔", color = Color(0xFF38BDF8), fontSize = 10.5.sp)
                                }
                                Icon(Icons.Filled.Visibility, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(18.dp))
                            }
                        }

                        Text("Write Status or Choose Quote", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = currentQuote,
                            onValueChange = { currentQuote = it; hasWatermark = true },
                            label = { Text("Quote / Status / Shayari") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

                        OutlinedTextField(
                            value = currentAuthor,
                            onValueChange = { currentAuthor = it; hasWatermark = true },
                            label = { Text("Author / Signature") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Popular Quotes & Shayari Presets", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            quotePresets.forEach { (quote, author) ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            currentQuote = quote
                                            currentAuthor = author
                                            hasWatermark = true
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                ) {
                                    Text(
                                        text = "$quote — $author",
                                        modifier = Modifier.padding(12.dp),
                                        fontSize = 12.sp,
                                        maxLines = 2
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Status Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                1 -> {
                    // TAB 1: Add Photo (Dedicated photo upload section requested by user!)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Text("Add Custom Status Photo 📸", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }

                                Text(
                                    "Pick any photo from your phone's gallery (Nature, Portrait, Scenery, Life). Your quote will be elegantly displayed with cinematic gradient shading!",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 17.sp
                                )

                                if (bgPhotoBitmap != null) {
                                    // Preview of chosen photo
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .border(2.dp, Color(0xFF059669), RoundedCornerShape(14.dp))
                                    ) {
                                        Image(
                                            bitmap = bgPhotoBitmap!!.asImageBitmap(),
                                            contentDescription = "Selected Background Photo",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )

                                        // Dark gradient overlay
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(Color.Black.copy(alpha = 0.4f), Color.Black.copy(alpha = 0.7f))
                                                    )
                                                )
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = currentQuote.take(60) + if (currentQuote.length > 60) "..." else "",
                                                color = Color.White,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center
                                            )
                                        }

                                        Surface(
                                            color = Color(0xFF059669),
                                            shape = RoundedCornerShape(bottomEnd = 10.dp),
                                            modifier = Modifier.align(Alignment.TopStart)
                                        ) {
                                            Text(
                                                "Active Photo ✓",
                                                color = Color.White,
                                                fontSize = 10.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                statusPhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                            },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(Modifier.width(6.dp))
                                            Text("Change Photo", fontSize = 12.sp)
                                        }

                                        OutlinedButton(
                                            onClick = {
                                                bgPhotoBitmap = null
                                                bgPhotoUri = null
                                                hasWatermark = true
                                            },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text("Remove Photo", color = Color(0xFFEF4444), fontSize = 12.sp)
                                        }
                                    }
                                } else {
                                    // Upload Card button
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(130.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .clickable {
                                                statusPhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                            },
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Surface(
                                                shape = CircleShape,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(46.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                                                }
                                            }
                                            Spacer(Modifier.height(8.dp))
                                            Text("Tap to Select Photo from Gallery", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                                            Text("Supports JPG, PNG, WEBP", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Status Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                2 -> {
                    // TAB 2: Background Gradients & Styling
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Gradient Background Styles", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(
                            "Choose a vibrant modern gradient backdrop if you don't want to use a photo:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            gradients.forEachIndexed { index, colors ->
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(colors))
                                        .border(
                                            width = if (selectedGradientIndex == index && bgPhotoBitmap == null) 3.5.dp else 1.dp,
                                            color = if (selectedGradientIndex == index && bgPhotoBitmap == null) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            selectedGradientIndex = index
                                            bgPhotoBitmap = null
                                            hasWatermark = true
                                        }
                                )
                            }
                        }

                        Text("Text Font Size", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Font Size", fontSize = 13.sp)
                            Text("${fontSizeSp.toInt()} sp", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = fontSizeSp,
                            onValueChange = { fontSizeSp = it },
                            valueRange = 14f..36f
                        )

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Status Preview & Save", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
                3 -> {
                    // TAB 3: Live Preview & Save
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Live Status Card (1:1 Square)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .shadow(8.dp, RoundedCornerShape(20.dp)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (bgPhotoBitmap != null) {
                                    Image(
                                        bitmap = bgPhotoBitmap!!.asImageBitmap(),
                                        contentDescription = "Status Background Photo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    // Dark overlay for sharp text contrast
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    listOf(
                                                        Color.Black.copy(alpha = 0.55f),
                                                        Color.Black.copy(alpha = 0.78f)
                                                    )
                                                )
                                            )
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Brush.linearGradient(gradients[selectedGradientIndex]))
                                    )
                                }

                                // Content overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(
                                            Icons.Filled.FormatQuote,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.6f),
                                            modifier = Modifier.size(36.dp)
                                        )

                                        Text(
                                            text = currentQuote,
                                            color = Color.White,
                                            fontSize = fontSizeSp.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            lineHeight = (fontSizeSp * 1.35f).sp
                                        )

                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "— $currentAuthor",
                                                color = Color.White.copy(alpha = 0.95f),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                fontStyle = FontStyle.Italic
                                            )
                                            if (hasWatermark) {
                                                Spacer(Modifier.height(6.dp))
                                                Surface(
                                                    color = Color.Black.copy(alpha = 0.5f),
                                                    shape = RoundedCornerShape(6.dp)
                                                ) {
                                                    Text(
                                                        "⚡ Created with OmniTools App",
                                                        color = Color.White,
                                                        fontSize = 9.sp,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Watermark Control Bar
                        WatermarkControlBar(
                            hasWatermark = hasWatermark,
                            onWatchAdToSaveClean = { saveWithoutWatermarkViaAd() }
                        )

                        // Primary Save Action: Remove Watermark & Save (Rewarded Video Ad)
                        Button(
                            onClick = { saveWithoutWatermarkViaAd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Remove Watermark & Save HD (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        // Free Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = true, bgPhotoBitmap = bgPhotoBitmap)
                                    ImageExportUtils.saveBitmapToGallery(context, bmp, "StatusPost_Free")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save (Free)")
                            }
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = true, bgPhotoBitmap = bgPhotoBitmap)
                                    ImageExportUtils.shareBitmap(context, bmp, "Status - $currentAuthor")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share Status")
                            }
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }
            }
        }
    }
}

private fun renderStatusBitmap(
    quote: String,
    author: String,
    colors: List<Color>,
    hasWatermark: Boolean,
    bgPhotoBitmap: Bitmap? = null
): Bitmap {
    val size = 1080
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    if (bgPhotoBitmap != null) {
        val minDim = Math.min(bgPhotoBitmap.width, bgPhotoBitmap.height)
        val srcX = (bgPhotoBitmap.width - minDim) / 2
        val srcY = (bgPhotoBitmap.height - minDim) / 2
        val srcRect = Rect(srcX, srcY, srcX + minDim, srcY + minDim)
        val dstRect = Rect(0, 0, size, size)
        canvas.drawBitmap(bgPhotoBitmap, srcRect, dstRect, null)

        val overlayPaint = Paint().apply {
            color = android.graphics.Color.argb(165, 0, 0, 0)
        }
        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), overlayPaint)
    } else {
        val bgPaint = Paint().apply {
            shader = android.graphics.LinearGradient(
                0f, 0f, size.toFloat(), size.toFloat(),
                colors.map { it.toArgb() }.toIntArray(),
                null,
                android.graphics.Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), bgPaint)
    }

    val textPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 48f
        isFakeBoldText = true
    }

    val words = quote.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""
    for (word in words) {
        if ((currentLine + word).length > 30) {
            lines.add(currentLine)
            currentLine = "$word "
        } else {
            currentLine += "$word "
        }
    }
    if (currentLine.isNotBlank()) lines.add(currentLine)

    val startY = (size / 2f) - ((lines.size * 65f) / 2f)
    lines.forEachIndexed { i, line ->
        canvas.drawText(line.trim(), size / 2f, startY + (i * 65f), textPaint)
    }

    // Author
    val authorPaint = Paint().apply {
        color = android.graphics.Color.argb(230, 255, 255, 255)
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 34f
    }
    canvas.drawText("— $author", size / 2f, size - 150f, authorPaint)

    if (hasWatermark) {
        val wmBg = Paint().apply {
            color = android.graphics.Color.argb(160, 0, 0, 0)
        }
        val wmText = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 28f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(size / 2f - 240f, size - 70f, size / 2f + 240f, size - 20f), 16f, 16f, wmBg)
        canvas.drawText("⚡ Made with OmniTools App", size / 2f, size - 36f, wmText)
    }

    return bitmap
}

// ==========================================
// 3. INVITATION CARD MAKER
// ==========================================

data class InvitationTheme(
    val name: String,
    val bgColors: List<Color>,
    val headerColor: Color,
    val titleColor: Color,
    val accentColor: Color,
    val borderColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationCardMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val eventTypes = listOf("Birthday Party 🎂", "Housewarming (गृह प्रवेश) 🏡", "Anniversary 🎉", "Baby Shower 👶", "Grand Opening 🚀", "Corporate Party 💼")
    val themes = listOf(
        InvitationTheme("Royal Gold", listOf(Color(0xFF1E1B4B), Color(0xFF312E81)), Color(0xFFFDE68A), Color.White, Color(0xFFF59E0B), Color(0xFFF59E0B)),
        InvitationTheme("Birthday Pink", listOf(Color(0xFF831843), Color(0xFFBE185D)), Color(0xFFFCE7F3), Color.White, Color(0xFFF472B6), Color(0xFFFBCFE8)),
        InvitationTheme("Emerald Luxe", listOf(Color(0xFF064E3B), Color(0xFF047857)), Color(0xFFD1FAE5), Color.White, Color(0xFF34D399), Color(0xFFA7F3D0)),
        InvitationTheme("Sunset Fire", listOf(Color(0xFF7C2D12), Color(0xFFC2410C)), Color(0xFFFEF3C7), Color.White, Color(0xFFFBBF24), Color(0xFFFDE68A)),
        InvitationTheme("Cosmic Dark", listOf(Color(0xFF0F172A), Color(0xFF1E293B)), Color(0xFFE0F2FE), Color.White, Color(0xFF38BDF8), Color(0xFF7DD3FC))
    )

    var selectedEvent by remember { mutableStateOf(eventTypes[0]) }
    var hostName by remember { mutableStateOf("Sharma Family") }
    var eventTitle by remember { mutableStateOf("Aarav's 5th Birthday Celebration!") }
    var date by remember { mutableStateOf("Saturday, October 24, 2026") }
    var time by remember { mutableStateOf("6:30 PM Onwards") }
    var venue by remember { mutableStateOf("Club House, Green Valley Heights, MG Road") }
    var rsvp by remember { mutableStateOf("RSVP: 9876543210") }
    var selectedThemeIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var hasWatermark by remember { mutableStateOf(true) }

    val activeTheme = themes[selectedThemeIndex]

    // Celebrant / Birthday Person Photo
    var celebrantPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var celebrantBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val celebrantPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            celebrantPhotoUri = uri
            try {
                val stream = context.contentResolver.openInputStream(uri)
                celebrantBitmap = BitmapFactory.decodeStream(stream)
                hasWatermark = true
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading celebrant photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderInvitationBitmap(selectedEvent, hostName, eventTitle, date, time, venue, rsvp, celebrantBitmap, activeTheme, hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Invitation_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next card will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderInvitationBitmap(selectedEvent, hostName, eventTitle, date, time, venue, rsvp, celebrantBitmap, activeTheme, hasWatermark = false)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Invitation_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Invitation & Birthday Maker") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Details & Text", fontWeight = FontWeight.SemiBold, fontSize = 11.sp) },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Add Photo 📸", fontWeight = FontWeight.SemiBold, fontSize = 11.sp) },
                        icon = { Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Themes 🎨", fontWeight = FontWeight.SemiBold, fontSize = 11.sp) },
                        icon = { Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("Preview & Save", fontWeight = FontWeight.SemiBold, fontSize = 11.sp) },
                        icon = { Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: Birthday / Event Details (Inputs placed at top so keyboard never covers them)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Quick Mini Preview Bar
                        Surface(
                            color = activeTheme.bgColors.first().copy(alpha = 0.9f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, activeTheme.borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTab = 3 }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "$eventTitle 🎂",
                                        color = activeTheme.titleColor,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("Tap to view full live card preview ➔", color = activeTheme.accentColor, fontSize = 10.5.sp)
                                }
                                Icon(Icons.Filled.Visibility, contentDescription = null, tint = activeTheme.accentColor, modifier = Modifier.size(18.dp))
                            }
                        }

                        Text("Select Occasion", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            eventTypes.forEach { event ->
                                FilterChip(
                                    selected = selectedEvent == event,
                                    onClick = { selectedEvent = event; hasWatermark = true },
                                    label = { Text(event) }
                                )
                            }
                        }

                        Text("Edit Invitation Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = hostName,
                            onValueChange = { hostName = it; hasWatermark = true },
                            label = { Text("Host / Family Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = eventTitle,
                            onValueChange = { eventTitle = it; hasWatermark = true },
                            label = { Text("Event Headline / Celebrant Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it; hasWatermark = true },
                            label = { Text("Event Date") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = time,
                            onValueChange = { time = it; hasWatermark = true },
                            label = { Text("Event Time") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = venue,
                            onValueChange = { venue = it; hasWatermark = true },
                            label = { Text("Venue / Location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = rsvp,
                            onValueChange = { rsvp = it; hasWatermark = true },
                            label = { Text("RSVP Contact Number") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Live Card Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                1 -> {
                    // TAB 1: Add Birthday / Celebrant Photo (Dedicated Photo Add Tab requested by user!)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.5.dp, activeTheme.borderColor.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = activeTheme.accentColor, modifier = Modifier.size(24.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Text("Birthday / Celebrant Photo 📸", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }

                                Text(
                                    "Add photo of birthday boy/girl or celebrant. The photo will appear inside a royal golden circular frame on the invitation card.",
                                    fontSize = 12.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                // Circular Frame Preview
                                Box(
                                    modifier = Modifier
                                        .size(130.dp)
                                        .clip(CircleShape)
                                        .border(4.dp, activeTheme.borderColor, CircleShape)
                                        .background(activeTheme.bgColors.first()),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (celebrantBitmap != null) {
                                        Image(
                                            bitmap = celebrantBitmap!!.asImageBitmap(),
                                            contentDescription = "Celebrant Photo",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = activeTheme.accentColor, modifier = Modifier.size(36.dp))
                                            Spacer(Modifier.height(4.dp))
                                            Text("No Photo", color = activeTheme.headerColor, fontSize = 11.sp)
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            celebrantPhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = activeTheme.accentColor),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(if (celebrantBitmap != null) "Change Photo" else "Upload Photo", color = Color.Black, fontWeight = FontWeight.Bold)
                                    }

                                    if (celebrantBitmap != null) {
                                        OutlinedButton(
                                            onClick = { celebrantBitmap = null; celebrantPhotoUri = null },
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(Icons.Filled.Close, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("Remove", color = Color(0xFFEF4444))
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Live Card Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                2 -> {
                    // TAB 2: Themes & Color Palette
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text("Select Invitation Theme Style", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        themes.forEachIndexed { index, theme ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedThemeIndex = index; hasWatermark = true },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(
                                    if (selectedThemeIndex == index) 2.5.dp else 1.dp,
                                    if (selectedThemeIndex == index) theme.borderColor else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Brush.linearGradient(theme.bgColors))
                                            .border(1.5.dp, theme.borderColor, RoundedCornerShape(10.dp))
                                    )
                                    Spacer(Modifier.width(14.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(theme.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text("Royal Gradient + Decorative Frame", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (selectedThemeIndex == index) {
                                        Icon(Icons.Filled.Check, contentDescription = null, tint = theme.accentColor, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 3 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full Live Card Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                3 -> {
                    // TAB 3: Preview & Save
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Live Card Canvas (3:4 ratio)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.75f)
                                .shadow(8.dp, RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(2.dp, activeTheme.borderColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(activeTheme.bgColors))
                                    .padding(18.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = hostName.uppercase() + " CORDIALLY INVITES YOU",
                                        color = activeTheme.headerColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )

                                    // Celebrant Photo Frame if added
                                    if (celebrantBitmap != null) {
                                        Box(
                                            modifier = Modifier
                                                .size(76.dp)
                                                .clip(CircleShape)
                                                .border(2.5.dp, activeTheme.borderColor, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                bitmap = celebrantBitmap!!.asImageBitmap(),
                                                contentDescription = "Celebrant Photo",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = selectedEvent.split(" ")[0],
                                            color = activeTheme.accentColor,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(Modifier.height(3.dp))
                                        Text(
                                            text = eventTitle,
                                            color = activeTheme.titleColor,
                                            fontSize = if (celebrantBitmap != null) 19.sp else 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("🗓  $date", color = activeTheme.accentColor, fontSize = 12.5.sp, fontWeight = FontWeight.Medium)
                                        Text("⏰  $time", color = Color.White.copy(alpha = 0.9f), fontSize = 11.5.sp)
                                        Spacer(Modifier.height(3.dp))
                                        Text("📍  $venue", color = Color.White, fontSize = 11.5.sp, textAlign = TextAlign.Center)
                                        Spacer(Modifier.height(4.dp))
                                        Text(rsvp, color = activeTheme.headerColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                                        if (hasWatermark) {
                                            Spacer(Modifier.height(6.dp))
                                            Surface(
                                                color = Color.Black.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    "⚡ Created with OmniTools App",
                                                    color = Color.White,
                                                    fontSize = 8.5.sp,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        WatermarkControlBar(
                            hasWatermark = hasWatermark,
                            onWatchAdToSaveClean = { saveWithoutWatermarkViaAd() }
                        )

                        // Primary Save Action: Remove Watermark & Save (Rewarded Video Ad)
                        Button(
                            onClick = { saveWithoutWatermarkViaAd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Remove Watermark & Save HD (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        // Export Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderInvitationBitmap(selectedEvent, hostName, eventTitle, date, time, venue, rsvp, celebrantBitmap, activeTheme, hasWatermark = true)
                                    ImageExportUtils.saveBitmapToGallery(context, bmp, "InvitationCard_Free")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save (Free)")
                            }
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderInvitationBitmap(selectedEvent, hostName, eventTitle, date, time, venue, rsvp, celebrantBitmap, activeTheme, hasWatermark = true)
                                    ImageExportUtils.shareBitmap(context, bmp, "Invitation - $eventTitle")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share")
                            }
                        }

                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

private fun renderInvitationBitmap(
    event: String,
    host: String,
    title: String,
    date: String,
    time: String,
    venue: String,
    rsvp: String,
    celebrantBitmap: Bitmap?,
    theme: InvitationTheme,
    hasWatermark: Boolean
): Bitmap {
    val width = 1080
    val height = 1350
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Background gradient from selected theme
    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            intArrayOf(theme.bgColors.first().toArgb(), theme.bgColors.last().toArgb()),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Border from theme
    val borderPaint = Paint().apply {
        color = theme.borderColor.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    canvas.drawRoundRect(RectF(30f, 30f, width - 30f, height - 30f), 30f, 30f, borderPaint)

    val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    // Host
    textPaint.color = theme.headerColor.toArgb()
    textPaint.textSize = 32f
    textPaint.isFakeBoldText = true
    canvas.drawText(host.uppercase() + " CORDIALLY INVITES YOU", width / 2f, 120f, textPaint)

    // Occasion
    textPaint.color = theme.accentColor.toArgb()
    textPaint.textSize = 38f
    canvas.drawText(event, width / 2f, 190f, textPaint)

    // Draw Celebrant Photo if present
    var nextY = 320f
    if (celebrantBitmap != null) {
        val photoRadius = 140f
        drawCircularBitmap(canvas, celebrantBitmap, width / 2f, 370f, photoRadius, theme.borderColor.toArgb(), 10f)
        nextY = 600f
    }

    // Event Title
    textPaint.color = theme.titleColor.toArgb()
    textPaint.textSize = 58f
    textPaint.isFakeBoldText = true
    canvas.drawText(title, width / 2f, nextY, textPaint)

    // Date & Time
    textPaint.color = theme.accentColor.toArgb()
    textPaint.textSize = 38f
    textPaint.isFakeBoldText = false
    canvas.drawText("🗓  $date", width / 2f, nextY + 110f, textPaint)

    textPaint.color = android.graphics.Color.argb(230, 255, 255, 255)
    textPaint.textSize = 34f
    canvas.drawText("⏰  $time", width / 2f, nextY + 180f, textPaint)

    // Venue
    textPaint.color = android.graphics.Color.WHITE
    textPaint.textSize = 34f
    canvas.drawText("📍  $venue", width / 2f, nextY + 270f, textPaint)

    // RSVP
    textPaint.color = theme.headerColor.toArgb()
    textPaint.textSize = 32f
    textPaint.isFakeBoldText = true
    canvas.drawText(rsvp, width / 2f, nextY + 360f, textPaint)

    if (hasWatermark) {
        val wmBg = Paint().apply { color = android.graphics.Color.argb(160, 0, 0, 0) }
        val wmText = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 28f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(width / 2f - 240f, height - 70f, width / 2f + 240f, height - 20f), 14f, 14f, wmBg)
        canvas.drawText("⚡ Created with OmniTools App", width / 2f, height - 36f, wmText)
    }

    return bitmap
}

// ==========================================
// 4. REELS MAKER SCREEN (9:16)
// ==========================================

data class ReelTheme(
    val name: String,
    val bgColors: List<Color>,
    val badgeColor: Color,
    val subtitleColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReelsMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val badges = listOf("HOT TIP 🔥", "MINDSET 🚀", "DID YOU KNOW? 💡", "DAILY MOTIVATION ✨", "TRENDING 📈", "SECRETS 🤫")
    val themes = listOf(
        ReelTheme("Neon Cyber", listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), Color(0xFF312E81)), Color(0xFFEC4899), Color(0xFF93C5FD)),
        ReelTheme("Sunset Passion", listOf(Color(0xFF431407), Color(0xFF7C2D12), Color(0xFFC2410C)), Color(0xFFF97316), Color(0xFFFDE68A)),
        ReelTheme("Royal Violet", listOf(Color(0xFF1E1B4B), Color(0xFF4C1D95), Color(0xFF6D28D9)), Color(0xFFA855F7), Color(0xFFDDD6FE)),
        ReelTheme("Emerald Glow", listOf(Color(0xFF022C22), Color(0xFF064E3B), Color(0xFF047857)), Color(0xFF10B981), Color(0xFFA7F3D0)),
        ReelTheme("Crimson Strike", listOf(Color(0xFF450A0A), Color(0xFF7F1D1D), Color(0xFF991B1B)), Color(0xFFEF4444), Color(0xFFFECACA)),
        ReelTheme("Carbon Stealth", listOf(Color(0xFF09090B), Color(0xFF18181B), Color(0xFF27272A)), Color(0xFF38BDF8), Color(0xFFE2E8F0))
    )

    var selectedBadge by remember { mutableStateOf(badges[0]) }
    var hookHeadline by remember { mutableStateOf("5 Habits That Will Change Your Life in 30 Days") }
    var subtitleText by remember { mutableStateOf("Save this reel so you don't forget it later!") }
    var handle by remember { mutableStateOf("@yourcreatorhandle") }
    var selectedThemeIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var hasWatermark by remember { mutableStateOf(true) }

    val activeTheme = themes[selectedThemeIndex]

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, activeTheme, hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Reel_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next reel will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, activeTheme, hasWatermark = false)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Reel_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Reels & Story Maker") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Hook & Text", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Themes 🎨", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Preview & Save", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: Hook & Text Inputs (Keyboard never obscures inputs)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Mini preview banner
                        Surface(
                            color = activeTheme.bgColors.first(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, activeTheme.badgeColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTab = 2 }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = hookHeadline.take(35) + if (hookHeadline.length > 35) "..." else "",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp
                                    )
                                    Text("9:16 Reel Hook • Tap to view preview ➔", color = activeTheme.badgeColor, fontSize = 10.5.sp)
                                }
                                Icon(Icons.Filled.Visibility, contentDescription = null, tint = activeTheme.badgeColor, modifier = Modifier.size(18.dp))
                            }
                        }

                        Text("Select Viral Attention Badge", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            badges.forEach { badge ->
                                FilterChip(
                                    selected = selectedBadge == badge,
                                    onClick = { selectedBadge = badge; hasWatermark = true },
                                    label = { Text(badge) }
                                )
                            }
                        }

                        Text("Edit Hook & Text", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = hookHeadline,
                            onValueChange = { hookHeadline = it; hasWatermark = true },
                            label = { Text("Catchy Reel Hook Headline") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )

                        OutlinedTextField(
                            value = subtitleText,
                            onValueChange = { subtitleText = it; hasWatermark = true },
                            label = { Text("Subtitle / Call to Action") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = handle,
                            onValueChange = { handle = it; hasWatermark = true },
                            label = { Text("Creator Handle / Channel (@username)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { selectedTab = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full 9:16 Reel Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                1 -> {
                    // TAB 1: Themes & Gradients
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text("Select Gradient Background Theme", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        themes.forEachIndexed { index, theme ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedThemeIndex = index; hasWatermark = true },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(
                                    if (selectedThemeIndex == index) 2.5.dp else 1.dp,
                                    if (selectedThemeIndex == index) theme.badgeColor else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Brush.verticalGradient(theme.bgColors))
                                            .border(1.5.dp, theme.badgeColor, RoundedCornerShape(12.dp))
                                    )
                                    Spacer(Modifier.width(14.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(theme.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text("9:16 High Contrast Gradient", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (selectedThemeIndex == index) {
                                        Icon(Icons.Filled.Check, contentDescription = null, tint = theme.badgeColor, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full 9:16 Reel Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                2 -> {
                    // TAB 2: Live Preview & Save
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 9:16 Vertical Preview
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.72f)
                                .aspectRatio(9f / 16f)
                                .align(Alignment.CenterHorizontally)
                                .shadow(12.dp, RoundedCornerShape(24.dp)),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(2.dp, activeTheme.badgeColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(activeTheme.bgColors))
                                    .padding(20.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    // Badge
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = activeTheme.badgeColor,
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text(
                                            text = selectedBadge,
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }

                                    // Center Hook
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = hookHeadline,
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 24.sp
                                        )
                                        Spacer(Modifier.height(10.dp))
                                        Text(
                                            text = subtitleText,
                                            color = activeTheme.subtitleColor,
                                            fontSize = 11.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    // Bottom Handle & Watermark
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = handle,
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        if (hasWatermark) {
                                            Spacer(Modifier.height(6.dp))
                                            Surface(
                                                color = Color.Black.copy(alpha = 0.6f),
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    "⚡ Made with OmniTools",
                                                    color = Color.White,
                                                    fontSize = 9.sp,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        WatermarkControlBar(
                            hasWatermark = hasWatermark,
                            onWatchAdToSaveClean = { saveWithoutWatermarkViaAd() }
                        )

                        // Primary Save Action: Remove Watermark & Save (Rewarded Video Ad)
                        Button(
                            onClick = { saveWithoutWatermarkViaAd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Remove Watermark & Save 9:16 (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        // Export Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, activeTheme, hasWatermark = true)
                                    ImageExportUtils.saveBitmapToGallery(context, bmp, "ReelCover_Free")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save (Free)")
                            }
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, activeTheme, hasWatermark = true)
                                    ImageExportUtils.shareBitmap(context, bmp, "Reel Story - $hookHeadline")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share")
                            }
                        }

                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

private fun renderReelBitmap(badge: String, hook: String, subtitle: String, handle: String, theme: ReelTheme, hasWatermark: Boolean): Bitmap {
    val width = 1080
    val height = 1920
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            intArrayOf(theme.bgColors.first().toArgb(), theme.bgColors.last().toArgb()),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Badge
    val badgePaint = Paint().apply { color = theme.badgeColor.toArgb() }
    canvas.drawRoundRect(RectF(width / 2f - 240f, 220f, width / 2f + 240f, 310f), 25f, 25f, badgePaint)

    val badgeTextPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 38f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.drawText(badge, width / 2f, 280f, badgeTextPaint)

    // Hook Text
    val hookPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 72f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    val words = hook.split(" ")
    val lines = mutableListOf<String>()
    var curr = ""
    for (w in words) {
        if ((curr + w).length > 22) {
            lines.add(curr)
            curr = "$w "
        } else curr += "$w "
    }
    if (curr.isNotBlank()) lines.add(curr)

    val startY = 850f
    lines.forEachIndexed { i, line ->
        canvas.drawText(line.trim(), width / 2f, startY + (i * 90f), hookPaint)
    }

    // Subtitle
    val subPaint = Paint().apply {
        color = theme.subtitleColor.toArgb()
        textSize = 42f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.drawText(subtitle, width / 2f, startY + (lines.size * 90f) + 60f, subPaint)

    // Bottom Handle
    val handlePaint = Paint().apply {
        color = android.graphics.Color.argb(180, 255, 255, 255)
        textSize = 38f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.drawText(handle, width / 2f, height - 160f, handlePaint)

    if (hasWatermark) {
        val wmBg = Paint().apply {
            color = android.graphics.Color.argb(160, 0, 0, 0)
        }
        val wmText = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 28f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(width / 2f - 240f, height - 90f, width / 2f + 240f, height - 35f), 16f, 16f, wmBg)
        canvas.drawText("⚡ Made with OmniTools App", width / 2f, height - 52f, wmText)
    }

    return bitmap
}

// ==========================================
// 5. YOUTUBE THUMBNAIL MAKER (16:9)
// ==========================================

data class ThumbnailBgDesign(
    val name: String,
    val category: String,
    val colors: List<Color>,
    val badgeColor: Color,
    val headlineBarColor: Color,
    val headlineTextColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeThumbnailMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val badges = listOf("4K ULTRA", "100% WORKING", "VIRAL", "STEP BY STEP", "NEW TRICK 🚀", "HOW TO", "DON'T MISS")

    // Multiple Background Designs requested by user!
    val bgDesigns = listOf(
        ThumbnailBgDesign("High CTR Red & Dark", "Viral / Drama", listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF7F1D1D)), Color(0xFFEF4444), Color(0xFFFBBF24), Color.Black),
        ThumbnailBgDesign("Cyber Blue & Purple", "Tech / Gaming", listOf(Color(0xFF0A0F1D), Color(0xFF1E1B4B), Color(0xFF2563EB)), Color(0xFF3B82F6), Color(0xFF38BDF8), Color.Black),
        ThumbnailBgDesign("Neon Emerald Cash", "Money / Success", listOf(Color(0xFF022C22), Color(0xFF064E3B), Color(0xFF059669)), Color(0xFF10B981), Color(0xFF34D399), Color.Black),
        ThumbnailBgDesign("Sunset Fire Orange", "Entertainment", listOf(Color(0xFF431407), Color(0xFF7C2D12), Color(0xFFEA580C)), Color(0xFFF97316), Color(0xFFFDE68A), Color.Black),
        ThumbnailBgDesign("Luxury Obsidian Gold", "Finance / Wealth", listOf(Color(0xFF09090B), Color(0xFF18181B), Color(0xFF78350F)), Color(0xFFD97706), Color(0xFFFBBF24), Color.Black),
        ThumbnailBgDesign("Deep Violet Mystery", "Documentary / Sci", listOf(Color(0xFF0F172A), Color(0xFF31104B), Color(0xFF6B21A8)), Color(0xFFA855F7), Color(0xFFE9D5FF), Color.Black),
        ThumbnailBgDesign("Pure Crimson Alert", "Breaking News", listOf(Color(0xFF450A0A), Color(0xFF7F1D1D), Color(0xFFDC2626)), Color(0xFFEF4444), Color(0xFFFFFFFF), Color.Black),
        ThumbnailBgDesign("Minimal Slate Modern", "Podcasts / Education", listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF334155)), Color(0xFF64748B), Color(0xFFF1F5F9), Color.Black)
    )

    var selectedBadge by remember { mutableStateOf(badges[0]) }
    var mainHeadline by remember { mutableStateOf("HOW TO GROW FAST ON YOUTUBE") }
    var secondaryHook by remember { mutableStateOf("10,000 SUBSCRIBERS IN 30 DAYS!") }
    var selectedBgIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showSafeZone by remember { mutableStateOf(false) }
    var hasWatermark by remember { mutableStateOf(true) }

    // Custom background image upload
    var customBgUri by remember { mutableStateOf<Uri?>(null) }
    var customBgBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val customBgPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            customBgUri = uri
            try {
                val stream = context.contentResolver.openInputStream(uri)
                customBgBitmap = BitmapFactory.decodeStream(stream)
                hasWatermark = true
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading thumbnail background", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val activeBg = bgDesigns[selectedBgIndex]

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, activeBg, customBgBitmap, hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "YTThumbnail_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next thumbnail will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, activeBg, customBgBitmap, hasWatermark = false)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "YTThumbnail_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("YouTube Thumbnail Maker") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Titles & Badges", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Background Designs 🎨", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Preview & Save", fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp) },
                        icon = { Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: Titles & Badges (Inputs at top so keyboard never hides them)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Quick Mini Preview
                        Surface(
                            color = activeBg.colors.first(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, activeBg.badgeColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTab = 2 }
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = mainHeadline.take(35) + if (mainHeadline.length > 35) "..." else "",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text("16:9 HD Thumbnail • Tap to view live preview ➔", color = activeBg.headlineBarColor, fontSize = 10.5.sp)
                                }
                                Icon(Icons.Filled.Visibility, contentDescription = null, tint = activeBg.headlineBarColor, modifier = Modifier.size(20.dp))
                            }
                        }

                        Text("Select Attention Badge", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            badges.forEach { badge ->
                                FilterChip(
                                    selected = selectedBadge == badge,
                                    onClick = { selectedBadge = badge; hasWatermark = true },
                                    label = { Text(badge) }
                                )
                            }
                        }

                        Text("Catchy Headlines (High CTR)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = mainHeadline,
                            onValueChange = { mainHeadline = it; hasWatermark = true },
                            label = { Text("Main Catchy Title (Top Highlighting Bar)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = secondaryHook,
                            onValueChange = { secondaryHook = it; hasWatermark = true },
                            label = { Text("Secondary Hook (Sub-headline Bar)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { selectedTab = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full 16:9 Thumbnail Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                1 -> {
                    // TAB 1: Multiple Background Designs (Requested by user!)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Custom image upload card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Column {
                                        Text("Upload Custom Background", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(if (customBgBitmap != null) "Custom image active ✓" else "Use your own gameplay/photo", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                if (customBgBitmap != null) {
                                    TextButton(onClick = { customBgBitmap = null; customBgUri = null }) {
                                        Text("Remove", color = Color(0xFFEF4444), fontSize = 12.sp)
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            customBgPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Upload", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Text("Choose High-CTR Background Gradient", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        bgDesigns.forEachIndexed { index, design ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBgIndex = index
                                        customBgBitmap = null // clear custom image to use preset
                                        hasWatermark = true
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(
                                    if (selectedBgIndex == index && customBgBitmap == null) 2.5.dp else 1.dp,
                                    if (selectedBgIndex == index && customBgBitmap == null) design.badgeColor else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp, 36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Brush.horizontalGradient(design.colors))
                                            .border(1.dp, design.badgeColor, RoundedCornerShape(8.dp))
                                    )
                                    Spacer(Modifier.width(14.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(design.name, fontWeight = FontWeight.Bold, fontSize = 14.5.sp)
                                        Text("Category: ${design.category}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (selectedBgIndex == index && customBgBitmap == null) {
                                        Icon(Icons.Filled.Check, contentDescription = null, tint = design.badgeColor, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { selectedTab = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("View Full 16:9 Thumbnail Preview", fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(180.dp))
                    }
                }

                2 -> {
                    // TAB 2: Live Preview & Save
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 16:9 Thumbnail Canvas
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .shadow(12.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(2.dp, activeBg.badgeColor)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (customBgBitmap != null) {
                                    Image(
                                        bitmap = customBgBitmap!!.asImageBitmap(),
                                        contentDescription = "Custom Background",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    // Slight dark gradient overlay for sharp text readability
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.horizontalGradient(
                                                    listOf(
                                                        Color.Black.copy(alpha = 0.55f),
                                                        Color.Black.copy(alpha = 0.35f)
                                                    )
                                                )
                                            )
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Brush.horizontalGradient(activeBg.colors))
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // Badge
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = activeBg.badgeColor
                                    ) {
                                        Text(
                                            text = selectedBadge,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }

                                    // High-contrast Headlines
                                    Column {
                                        Surface(
                                            color = activeBg.headlineBarColor,
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.padding(bottom = 6.dp)
                                        ) {
                                            Text(
                                                text = " $mainHeadline ",
                                                color = activeBg.headlineTextColor,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                        Surface(
                                            color = Color.White,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = " $secondaryHook ",
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("HD 1080P • HIGH CTR DESIGN", color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp)
                                        if (hasWatermark) {
                                            Surface(
                                                color = Color.Black.copy(alpha = 0.7f),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    "⚡ OmniTools",
                                                    color = Color.White,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Safe Zone indicator
                                if (showSafeZone) {
                                    Surface(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp),
                                        color = Color.Red.copy(alpha = 0.75f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text("YT Timecode Area (0:00)", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(4.dp))
                                    }
                                }
                            }
                        }

                        // Safe Zone Toggle Button
                        OutlinedButton(
                            onClick = { showSafeZone = !showSafeZone },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(if (showSafeZone) "Hide Timecode Safe-Zone" else "Show YouTube Safe-Zone Overlay")
                        }

                        WatermarkControlBar(
                            hasWatermark = hasWatermark,
                            onWatchAdToSaveClean = { saveWithoutWatermarkViaAd() }
                        )

                        // Primary Save Action: Remove Watermark & Save (Rewarded Video Ad)
                        Button(
                            onClick = { saveWithoutWatermarkViaAd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Remove Watermark & Save 1280x720 (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        // Export Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, activeBg, customBgBitmap, hasWatermark = true)
                                    ImageExportUtils.saveBitmapToGallery(context, bmp, "YTThumbnail_Free")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Save (Free)")
                            }
                            OutlinedButton(
                                onClick = {
                                    val bmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, activeBg, customBgBitmap, hasWatermark = true)
                                    ImageExportUtils.shareBitmap(context, bmp, "YouTube Thumbnail - $mainHeadline")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share")
                            }
                        }

                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

private fun renderThumbnailBitmap(
    badge: String,
    main: String,
    sub: String,
    bgDesign: ThumbnailBgDesign,
    customBitmap: Bitmap?,
    hasWatermark: Boolean
): Bitmap {
    val width = 1280
    val height = 720
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    if (customBitmap != null) {
        val srcRect = Rect(0, 0, customBitmap.width, customBitmap.height)
        val dstRect = Rect(0, 0, width, height)
        val paint = Paint().apply { isFilterBitmap = true }
        canvas.drawBitmap(customBitmap, srcRect, dstRect, paint)

        // Overlay gradient for readability
        val overlayPaint = Paint().apply {
            color = android.graphics.Color.argb(120, 0, 0, 0)
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)
    } else {
        val bgPaint = Paint().apply {
            shader = android.graphics.LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                intArrayOf(bgDesign.colors.first().toArgb(), bgDesign.colors[1].toArgb(), bgDesign.colors.last().toArgb()),
                null,
                android.graphics.Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)
    }

    // Badge
    val badgePaint = Paint().apply { color = bgDesign.badgeColor.toArgb() }
    canvas.drawRoundRect(RectF(60f, 60f, 360f, 130f), 12f, 12f, badgePaint)

    val badgeText = Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 34f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.drawText(badge, 210f, 110f, badgeText)

    // Main Title Box
    val headlineBarPaint = Paint().apply { color = bgDesign.headlineBarColor.toArgb() }
    canvas.drawRoundRect(RectF(60f, 240f, 1100f, 360f), 16f, 16f, headlineBarPaint)

    val mainTextPaint = Paint().apply {
        color = bgDesign.headlineTextColor.toArgb()
        textSize = 62f
        isFakeBoldText = true
        isAntiAlias = true
    }
    canvas.drawText(main, 90f, 325f, mainTextPaint)

    // Subtitle Box
    val whitePaint = Paint().apply { color = android.graphics.Color.WHITE }
    canvas.drawRoundRect(RectF(60f, 390f, 960f, 490f), 16f, 16f, whitePaint)

    val subText = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 50f
        isFakeBoldText = true
        isAntiAlias = true
    }
    canvas.drawText(sub, 90f, 460f, subText)

    if (hasWatermark) {
        val wmBg = Paint().apply {
            color = android.graphics.Color.argb(180, 0, 0, 0)
        }
        val wmText = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 28f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(width / 2f - 240f, height - 70f, width / 2f + 240f, height - 20f), 14f, 14f, wmBg)
        canvas.drawText("⚡ Created with OmniTools App", width / 2f, height - 36f, wmText)
    }

    return bitmap
}
