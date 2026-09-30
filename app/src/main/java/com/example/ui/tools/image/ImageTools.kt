package com.example.ui.tools.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.TopBannerAd
import com.example.util.ImageExportUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

// ==========================================
// 1. IMAGE COMPRESSOR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCompressorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var compressedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var qualityPercent by remember { mutableFloatStateOf(70f) }
    var originalSizeKb by remember { mutableIntStateOf(0) }
    var compressedSizeKb by remember { mutableIntStateOf(0) }
    var isProcessing by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            scope.launch(Dispatchers.IO) {
                isProcessing = true
                try {
                    val stream = context.contentResolver.openInputStream(uri)
                    val bytes = stream?.readBytes() ?: byteArrayOf()
                    stream?.close()
                    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    withContext(Dispatchers.Main) {
                        originalBitmap = bmp
                        originalSizeKb = bytes.size / 1024
                        compressBitmap(bmp, qualityPercent.toInt()) { compBmp, compSize ->
                            compressedBitmap = compBmp
                            compressedSizeKb = compSize
                            isProcessing = false
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) { isProcessing = false }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Image Compressor") },
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
            // Pick Image Area
            if (originalBitmap == null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(10.dp))
                        Text("Select Photo to Compress", fontWeight = FontWeight.Bold)
                        Text("Works with JPG, PNG, WEBP (No Quality Loss)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                // Image Comparison Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            compressedBitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "Compressed",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } ?: CircularProgressIndicator()
                        }

                        // Size comparison badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Original Size", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${originalSizeKb} KB", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Compressed Size", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    "${compressedSizeKb} KB",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF059669)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Saved", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                val savedPct = if (originalSizeKb > 0) ((originalSizeKb - compressedSizeKb) * 100) / originalSizeKb else 0
                                Text("${maxOf(0, savedPct)}%", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                // Slider
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Compression Quality:", fontWeight = FontWeight.Bold)
                            Text("${qualityPercent.toInt()}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = qualityPercent,
                            onValueChange = {
                                qualityPercent = it
                                originalBitmap?.let { bmp ->
                                    compressBitmap(bmp, it.toInt()) { compBmp, compSize ->
                                        compressedBitmap = compBmp
                                        compressedSizeKb = compSize
                                    }
                                }
                            },
                            valueRange = 10f..95f
                        )
                    }
                }

                // Export Actions
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            compressedBitmap?.let {
                                ImageExportUtils.saveBitmapToGallery(context, it, "Compressed")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Save to Gallery")
                    }
                    OutlinedButton(
                        onClick = {
                            compressedBitmap?.let {
                                ImageExportUtils.shareBitmap(context, it, "Compressed Image")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Share")
                    }
                }

                OutlinedButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select Another Photo")
                }
            }
        }
    }
}

private fun compressBitmap(source: Bitmap, quality: Int, onResult: (Bitmap, Int) -> Unit) {
    val stream = ByteArrayOutputStream()
    source.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    val byteArray = stream.toByteArray()
    val compressed = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    onResult(compressed, byteArray.size / 1024)
}

// ==========================================
// 2. PASSPORT PHOTO MAKER SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportPhotoMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var sourceBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedBgColor by remember { mutableStateOf(Color(0xFF2563EB)) } // Royal Blue
    var passportBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val bgColors = listOf(
        "Studio Blue" to Color(0xFF2563EB),
        "Pure White" to Color(0xFFFFFFFF),
        "Soft Gray" to Color(0xFFE2E8F0),
        "Sky Blue" to Color(0xFF38BDF8)
    )

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                val stream = context.contentResolver.openInputStream(uri)
                val bmp = BitmapFactory.decodeStream(stream)
                stream?.close()
                withContext(Dispatchers.Main) {
                    sourceBitmap = bmp
                    passportBitmap = renderPassportPhoto(bmp, selectedBgColor)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Passport Photo Maker") },
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
            if (passportBitmap == null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(10.dp))
                        Text("Upload Your Portrait Photo", fontWeight = FontWeight.Bold)
                        Text("Auto creates 35x45mm (2x2 inch) passport standard", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                // Passport Preview (Standard 35x45mm aspect ratio)
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .aspectRatio(35f / 45f)
                        .align(Alignment.CenterHorizontally)
                        .shadow(8.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.LightGray)
                ) {
                    passportBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Passport Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Background Color Chooser
                Text("Select Studio Background Color", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    bgColors.forEach { (name, color) ->
                        FilterChip(
                            selected = selectedBgColor == color,
                            onClick = {
                                selectedBgColor = color
                                sourceBitmap?.let {
                                    passportBitmap = renderPassportPhoto(it, color)
                                }
                            },
                            label = { Text(name) },
                            leadingIcon = {
                                Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(color).border(1.dp, Color.Gray, CircleShape))
                            }
                        )
                    }
                }

                // Actions: Save Single Photo & Save 6-Photo Print Sheet (4x6 inch paper)
                Button(
                    onClick = {
                        passportBitmap?.let {
                            val sheetBmp = renderSixPhotoSheet(it)
                            ImageExportUtils.saveBitmapToGallery(context, sheetBmp, "Passport_6_PrintSheet")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Download, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Save 6-Photo Print Sheet (4x6 Inch)")
                }

                OutlinedButton(
                    onClick = {
                        passportBitmap?.let {
                            ImageExportUtils.saveBitmapToGallery(context, it, "Passport_Photo")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Single Passport Photo")
                }
            }
        }
    }
}

private fun renderPassportPhoto(source: Bitmap, bgColor: Color): Bitmap {
    val width = 413 // Standard 35mm at 300 DPI
    val height = 531 // Standard 45mm at 300 DPI
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    canvas.drawColor(bgColor.toArgb())

    // Center crop source bitmap onto the passport canvas
    val srcWidth = source.width
    val srcHeight = source.height

    val srcRect = if (srcWidth > srcHeight) {
        val offset = (srcWidth - srcHeight) / 2
        Rect(offset, 0, offset + srcHeight, srcHeight)
    } else {
        val offset = (srcHeight - srcWidth) / 4
        Rect(0, offset, srcWidth, offset + srcWidth)
    }

    val dstRect = Rect(0, 0, width, height)
    canvas.drawBitmap(source, srcRect, dstRect, Paint(Paint.FILTER_BITMAP_FLAG))

    return bitmap
}

private fun renderSixPhotoSheet(passportPhoto: Bitmap): Bitmap {
    val sheetWidth = 1200 // 4 inch at 300 DPI
    val sheetHeight = 1800 // 6 inch at 300 DPI
    val sheetBitmap = Bitmap.createBitmap(sheetWidth, sheetHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(sheetBitmap)
    canvas.drawColor(AndroidColor.WHITE)

    val paint = Paint(Paint.FILTER_BITMAP_FLAG)
    val borderPaint = Paint().apply {
        color = AndroidColor.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    val pW = 450
    val pH = 580
    val gapX = (sheetWidth - (pW * 2)) / 3
    val gapY = (sheetHeight - (pH * 3)) / 4

    for (row in 0..2) {
        for (col in 0..1) {
            val left = gapX + col * (pW + gapX)
            val top = gapY + row * (pH + gapY)
            val dstRect = Rect(left, top, left + pW, top + pH)
            canvas.drawBitmap(passportPhoto, null, dstRect, paint)
            canvas.drawRect(RectF(left.toFloat(), top.toFloat(), (left + pW).toFloat(), (top + pH).toFloat()), borderPaint)
        }
    }

    return sheetBitmap
}

// ==========================================
// 3. IMAGE QUALITY CHECKER & BLUR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageQualityCheckerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageBmp by remember { mutableStateOf<Bitmap?>(null) }
    var widthPx by remember { mutableIntStateOf(0) }
    var heightPx by remember { mutableIntStateOf(0) }
    var megapixels by remember { mutableFloatStateOf(0f) }
    var qualityRating by remember { mutableStateOf("Ready to inspect") }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                val stream = context.contentResolver.openInputStream(uri)
                val bmp = BitmapFactory.decodeStream(stream)
                stream?.close()
                bmp?.let {
                    withContext(Dispatchers.Main) {
                        imageBmp = it
                        widthPx = it.width
                        heightPx = it.height
                        val mp = (it.width * it.height) / 1_000_000f
                        megapixels = mp
                        qualityRating = when {
                            mp >= 12.0f -> "4K Ultra HD • Professional Print Ready"
                            mp >= 5.0f -> "Full HD (1080p) • Excellent Quality"
                            mp >= 2.0f -> "Standard HD (720p) • Good for Social Media"
                            else -> "Low Resolution • May appear blurry when printed"
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Image Quality Checker") },
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
            Button(
                onClick = { picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Select Image to Inspect")
            }

            imageBmp?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Image Analysis", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        HorizontalDivider()

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Resolution:")
                            Text("$widthPx × $heightPx px", fontWeight = FontWeight.SemiBold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Megapixels:")
                            Text("${String.format("%.2f", megapixels)} MP", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Aspect Ratio:")
                            val gcd = findGcd(widthPx, heightPx)
                            Text("${widthPx / gcd}:${heightPx / gcd}", fontWeight = FontWeight.SemiBold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Quality Rating:")
                            Text(qualityRating, fontWeight = FontWeight.Bold, color = if (megapixels >= 5f) Color(0xFF059669) else Color(0xFFE11D48))
                        }
                    }
                }
            }
        }
    }
}

private fun findGcd(a: Int, b: Int): Int = if (b == 0) a else findGcd(b, a % b)
