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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.OndemandVideo
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
import androidx.compose.material3.Surface
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

            // BRIDE & GROOM PHOTO SECTION
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
                                            .size(54.dp)
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
                                        modifier = Modifier.size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
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
                                            .size(54.dp)
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
                                        modifier = Modifier.size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
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

            // Theme Selector Chips
            Text("Select Theme Design", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                themes.forEachIndexed { index, theme ->
                    FilterChip(
                        selected = selectedThemeIndex == index,
                        onClick = {
                            selectedThemeIndex = index
                            hasWatermark = true // reset for new edit
                        },
                        label = { Text(theme.name) },
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(theme.bgColors.first())
                            )
                        }
                    )
                }
            }

            // Inputs (editing resets single-use watermark removal)
            Text("Edit Card Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = brideName, onValueChange = { brideName = it; hasWatermark = true }, label = { Text("Bride's Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = groomName, onValueChange = { groomName = it; hasWatermark = true }, label = { Text("Groom's Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = weddingDate, onValueChange = { weddingDate = it; hasWatermark = true }, label = { Text("Wedding Date") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = weddingTime, onValueChange = { weddingTime = it; hasWatermark = true }, label = { Text("Wedding Time / Muhurat") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = venue, onValueChange = { venue = it; hasWatermark = true }, label = { Text("Venue / Location Address") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = rsvp, onValueChange = { rsvp = it; hasWatermark = true }, label = { Text("RSVP / Contact Info") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = tagline, onValueChange = { tagline = it; hasWatermark = true }, label = { Text("Tagline / Welcome Message") }, modifier = Modifier.fillMaxWidth())
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
    var fontSizeSp by remember { mutableFloatStateOf(20f) }
    var hasWatermark by remember { mutableStateOf(true) }

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
                    val cleanBmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Status_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next image will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = false)
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
            // Live Status Card (1:1 Square)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(gradients[selectedGradientIndex]))
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
                            tint = Color.White.copy(alpha = 0.5f),
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
                                color = Color.White.copy(alpha = 0.9f),
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
                        val bmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = true)
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
                        val bmp = renderStatusBitmap(currentQuote, currentAuthor, gradients[selectedGradientIndex], hasWatermark = true)
                        ImageExportUtils.shareBitmap(context, bmp, "Status - $currentAuthor")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share Status")
                }
            }

            // Quick Quote Chips
            Text("Popular Quotes & Shayari", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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

            // Background selector
            Text("Background Style", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                gradients.forEachIndexed { index, colors ->
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(colors))
                            .border(
                                width = if (selectedGradientIndex == index) 3.dp else 0.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable { selectedGradientIndex = index }
                    )
                }
            }

            // Text Inputs
            OutlinedTextField(
                value = currentQuote,
                onValueChange = { currentQuote = it; hasWatermark = true },
                label = { Text("Quote / Status Text") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            OutlinedTextField(
                value = currentAuthor,
                onValueChange = { currentAuthor = it; hasWatermark = true },
                label = { Text("Author / Tag") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun renderStatusBitmap(quote: String, author: String, colors: List<Color>, hasWatermark: Boolean): Bitmap {
    val size = 1080
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, size.toFloat(), size.toFloat(),
            colors.map { it.toArgb() }.toIntArray(),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), bgPaint)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationCardMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val eventTypes = listOf("Birthday Party 🎂", "Housewarming (गृह प्रवेश) 🏡", "Anniversary 🎉", "Baby Shower 👶", "Grand Opening 🚀", "Corporate Party 💼")
    var selectedEvent by remember { mutableStateOf(eventTypes[0]) }
    var hostName by remember { mutableStateOf("Sharma Family") }
    var eventTitle by remember { mutableStateOf("Aarav's 5th Birthday Celebration!") }
    var date by remember { mutableStateOf("Saturday, October 24, 2026") }
    var time by remember { mutableStateOf("6:30 PM Onwards") }
    var venue by remember { mutableStateOf("Club House, Green Valley Heights, MG Road") }
    var rsvp by remember { mutableStateOf("RSVP: 9876543210") }
    var hasWatermark by remember { mutableStateOf(true) }

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderStatusBitmap(eventTitle, "$date • $venue", listOf(Color(0xFF1E1B4B), Color(0xFF312E81)), hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Invitation_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next card will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderStatusBitmap(eventTitle, "$date • $venue", listOf(Color(0xFF1E1B4B), Color(0xFF312E81)), hasWatermark = false)
            ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Invitation_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Invitation Card Maker") },
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
            // Preview Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFFF59E0B))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFF1E1B4B), Color(0xFF312E81))))
                        .padding(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = hostName.uppercase() + " CORDIALLY INVITES YOU",
                            color = Color(0xFFFDE68A),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = selectedEvent.split(" ")[0],
                                color = Color(0xFFF59E0B),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = eventTitle,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🗓  $date", color = Color(0xFFFBBF24), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text("⏰  $time", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("📍  $venue", color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center)
                            Spacer(Modifier.height(6.dp))
                            Text(rsvp, color = Color(0xFFFDE68A), fontSize = 11.sp, fontWeight = FontWeight.Bold)

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
                        val bmp = renderStatusBitmap(eventTitle, "$date • $venue", listOf(Color(0xFF1E1B4B), Color(0xFF312E81)), hasWatermark = true)
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
                        val bmp = renderStatusBitmap(eventTitle, "$date • $venue", listOf(Color(0xFF1E1B4B), Color(0xFF312E81)), hasWatermark = true)
                        ImageExportUtils.shareBitmap(context, bmp, "Invitation - $eventTitle")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share")
                }
            }

            // Event Type Chips
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

            // Inputs
            OutlinedTextField(value = hostName, onValueChange = { hostName = it; hasWatermark = true }, label = { Text("Host / Family Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = eventTitle, onValueChange = { eventTitle = it; hasWatermark = true }, label = { Text("Event Headline") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = date, onValueChange = { date = it; hasWatermark = true }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = time, onValueChange = { time = it; hasWatermark = true }, label = { Text("Time") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = venue, onValueChange = { venue = it; hasWatermark = true }, label = { Text("Venue / Location") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = rsvp, onValueChange = { rsvp = it; hasWatermark = true }, label = { Text("RSVP Contact") }, modifier = Modifier.fillMaxWidth())
        }
    }
}

// ==========================================
// 4. REELS MAKER SCREEN (9:16)
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReelsMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val badges = listOf("HOT TIP 🔥", "MINDSET 🚀", "DID YOU KNOW? 💡", "DAILY MOTIVATION ✨", "TRENDING 📈", "SECRETS 🤫")
    var selectedBadge by remember { mutableStateOf(badges[0]) }
    var hookHeadline by remember { mutableStateOf("5 Habits That Will Change Your Life in 30 Days") }
    var subtitleText by remember { mutableStateOf("Save this reel so you don't forget it later!") }
    var handle by remember { mutableStateOf("@yourcreatorhandle") }
    var hasWatermark by remember { mutableStateOf(true) }

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "Reel_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next reel will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, hasWatermark = false)
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
            // 9:16 Vertical Preview
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(9f / 16f)
                    .align(Alignment.CenterHorizontally)
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, Color(0xFF6366F1))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), Color(0xFF312E81))))
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
                            color = Color(0xFFEC4899),
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
                                color = Color(0xFF93C5FD),
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
                        val bmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, hasWatermark = true)
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
                        val bmp = renderReelBitmap(selectedBadge, hookHeadline, subtitleText, handle, hasWatermark = true)
                        ImageExportUtils.shareBitmap(context, bmp, "Reel Story - $hookHeadline")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share")
                }
            }

            // Badge Chips
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

            // Inputs
            OutlinedTextField(value = hookHeadline, onValueChange = { hookHeadline = it; hasWatermark = true }, label = { Text("Catchy Reel Hook Headline") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = subtitleText, onValueChange = { subtitleText = it; hasWatermark = true }, label = { Text("Subtitle / Description") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = handle, onValueChange = { handle = it; hasWatermark = true }, label = { Text("Creator Handle / Channel") }, modifier = Modifier.fillMaxWidth())
        }
    }
}

private fun renderReelBitmap(badge: String, hook: String, subtitle: String, handle: String, hasWatermark: Boolean): Bitmap {
    val width = 1080
    val height = 1920
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            intArrayOf(android.graphics.Color.parseColor("#0F172A"), android.graphics.Color.parseColor("#1E1B4B"), android.graphics.Color.parseColor("#312E81")),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Badge
    val badgePaint = Paint().apply { color = android.graphics.Color.parseColor("#EC4899") }
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
        color = android.graphics.Color.parseColor("#93C5FD")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeThumbnailMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    val badges = listOf("4K ULTRA", "100% WORKING", "VIRAL", "STEP BY STEP", "NEW TRICK 🚀", "HOW TO", "DON'T MISS")
    var selectedBadge by remember { mutableStateOf(badges[0]) }
    var mainHeadline by remember { mutableStateOf("HOW TO GROW FAST ON YOUTUBE") }
    var secondaryHook by remember { mutableStateOf("10,000 SUBSCRIBERS IN 30 DAYS!") }
    var showSafeZone by remember { mutableStateOf(false) }
    var hasWatermark by remember { mutableStateOf(true) }

    fun saveWithoutWatermarkViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val cleanBmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, hasWatermark = false)
                    ImageExportUtils.saveBitmapToGallery(context, cleanBmp, "YTThumbnail_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next thumbnail will require watching video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val cleanBmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, hasWatermark = false)
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
            // 16:9 Thumbnail Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .shadow(12.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFFEF4444))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.horizontalGradient(listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF7F1D1D))))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFEF4444)
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
                                color = Color(0xFFFBBF24),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.padding(bottom = 6.dp)
                            ) {
                                Text(
                                    text = " $mainHeadline ",
                                    color = Color.Black,
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
                            color = Color.Red.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("YT Timecode Area (0:00)", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(4.dp))
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
                Text("Remove Watermark & Save 1280x720 (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            // Export Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = {
                        val bmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, hasWatermark = true)
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
                        val bmp = renderThumbnailBitmap(selectedBadge, mainHeadline, secondaryHook, hasWatermark = true)
                        ImageExportUtils.shareBitmap(context, bmp, "YouTube Thumbnail - $mainHeadline")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share")
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

            // Badges
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

            // Text Inputs
            OutlinedTextField(value = mainHeadline, onValueChange = { mainHeadline = it; hasWatermark = true }, label = { Text("Main Catchy Title (Yellow Bar)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = secondaryHook, onValueChange = { secondaryHook = it; hasWatermark = true }, label = { Text("Secondary Hook (White Bar)") }, modifier = Modifier.fillMaxWidth())
        }
    }
}

private fun renderThumbnailBitmap(badge: String, main: String, sub: String, hasWatermark: Boolean): Bitmap {
    val width = 1280
    val height = 720
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            intArrayOf(android.graphics.Color.parseColor("#0F172A"), android.graphics.Color.parseColor("#1E1B4B"), android.graphics.Color.parseColor("#7F1D1D")),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Badge
    val badgePaint = Paint().apply { color = android.graphics.Color.parseColor("#EF4444") }
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
    val yellowPaint = Paint().apply { color = android.graphics.Color.parseColor("#FBBF24") }
    canvas.drawRoundRect(RectF(60f, 240f, 1100f, 360f), 16f, 16f, yellowPaint)

    val blackText = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 62f
        isFakeBoldText = true
        isAntiAlias = true
    }
    canvas.drawText(main, 90f, 325f, blackText)

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
