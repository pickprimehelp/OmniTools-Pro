package com.example.ui.tools.fonts

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdManager
import com.example.ads.TopBannerAd
import com.example.ui.tools.design.WatermarkControlBar
import com.example.util.ImageExportUtils

data class FontVariant(
    val id: String,
    val name: String,
    val sampleText: String,
    val transform: (String) -> String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontsMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val activity = context as? Activity

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Multiple Fonts List, 1: Colour Font Designer
    var inputText by remember { mutableStateOf("OmniTools VIP") }

    // Color Font Designer States
    val colorPalettes = listOf(
        "Neon Cyan" to Color(0xFF00E5FF),
        "Flaming Gold" to Color(0xFFFFD700),
        "Cyber Green" to Color(0xFF00E676),
        "Electric Pink" to Color(0xFFFF4081),
        "Royal Violet" to Color(0xFFB388FF),
        "Sunset Orange" to Color(0xFFFF6E40),
        "Pure White" to Color(0xFFFFFFFF)
    )
    val backgroundGradients = listOf(
        "Dark Luxury" to listOf(Color(0xFF0F172A), Color(0xFF1E293B)),
        "Cyberpunk" to listOf(Color(0xFF1E1B4B), Color(0xFF4C1D95), Color(0xFF831843)),
        "Emerald Glow" to listOf(Color(0xFF064E3B), Color(0xFF047857), Color(0xFF065F46)),
        "Sunset Fire" to listOf(Color(0xFF7C2D12), Color(0xFFC2410C), Color(0xFFEA580C)),
        "AMOLED Black" to listOf(Color(0xFF000000), Color(0xFF111827)),
        "Royal Velvet" to listOf(Color(0xFF4A0E17), Color(0xFF881337))
    )

    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var selectedBgIndex by remember { mutableIntStateOf(0) }
    var fontSizeSp by remember { mutableFloatStateOf(26f) }
    var fontStyleName by remember { mutableStateOf("Bold Serif") }
    var hasWatermark by remember { mutableStateOf(true) }

    // List of 22+ Unicode Font Transformations
    val fontVariants = remember {
        listOf(
            FontVariant("bold_serif", "Bold Serif", "𝐁𝐨𝐥𝐝 𝐒𝐞𝐫𝐢𝐟") { convertMathSerifBold(it) },
            FontVariant("bold_sans", "Bold Sans", "𝗕𝗼𝗹𝗱 𝗦𝗮𝗻𝘀") { convertMathSansBold(it) },
            FontVariant("italic_serif", "Italic Serif", "𝐼𝑡𝑎𝑙𝑖𝑐 𝑆𝑒𝑟𝑖𝑓") { convertMathSerifItalic(it) },
            FontVariant("bold_italic", "Bold Italic", "𝑩𝒐𝒍𝒅 𝑰𝒕𝒂𝒍𝒊𝒄") { convertMathBoldItalic(it) },
            FontVariant("cursive", "Cursive / Script", "𝒞𝓊𝓇𝓈𝒾𝓋𝑒 𝒮𝒸𝓇𝒾𝓅𝓉") { convertScript(it) },
            FontVariant("bold_cursive", "Bold Cursive", "𝓒𝓾𝓻𝓼𝓲𝓿𝓮 𝓑𝓸𝓵𝓭") { convertBoldScript(it) },
            FontVariant("fraktur", "Gothic / Fraktur", "𝕱𝖗𝖆𝖐𝖙𝖚𝖗 𝕲𝖔𝖙𝖍𝖎𝖈") { convertFraktur(it) },
            FontVariant("bold_fraktur", "Bold Gothic", "𝕭𝖔𝖑𝖉 𝕲𝖔𝖙𝖍𝖎𝖈") { convertBoldFraktur(it) },
            FontVariant("double_struck", "Double Struck", "𝔻𝕠𝕦𝕓𝕝𝕖 𝕊𝕥𝕣𝕦𝕔𝕜") { convertDoubleStruck(it) },
            FontVariant("bubble", "Circled / Bubble", "Ⓒⓘⓡⓒⓛⓔⓓ") { convertCircled(it) },
            FontVariant("bubble_filled", "Filled Bubble", "🅒🅘🅡🅒🅛🅔🅓") { convertCircledFilled(it) },
            FontVariant("squared", "Squared / Boxed", "🅂🅀🅄🄰🅁🄴🄳") { convertSquared(it) },
            FontVariant("monospace", "Monospace Code", "𝙼𝚘𝚗𝚘𝚜𝚙𝚊𝚌𝚎") { convertMonospace(it) },
            FontVariant("small_caps", "Small Caps", "ꜱᴍᴀʟʟ ᴄᴀᴘꜱ") { convertSmallCaps(it) },
            FontVariant("inverted", "Upside Down", "uʍop ǝpısd∩") { convertUpsideDown(it) },
            FontVariant("strikethrough", "Strikethrough", "S̶t̶r̶i̶k̶e̶") { convertStrikethrough(it) },
            FontVariant("underline", "Underline", "U̲n̲d̲e̲r̲l̲i̲n̲e̲") { convertUnderline(it) },
            FontVariant("slash", "Slash Through", "S̷l̷a̷s̷h̷") { convertSlash(it) },
            FontVariant("aesthetic", "Aesthetic / Fullwidth", "Ｆｕｌｌｗｉｄｔｈ") { convertFullwidth(it) },
            FontVariant("sparkles", "Magic Sparkles", "✨ Text ✨") { "✨ $it ✨" },
            FontVariant("royal", "Crown Royal", "👑 Text 👑") { "👑 $it 👑" },
            FontVariant("fire", "Trending Fire", "🔥 Text 🔥") { "🔥 $it 🔥" },
            FontVariant("vip", "VIP Diamond", "💎 Text 💎") { "💎 $it 💎" }
        )
    }

    fun saveCleanCardViaAd() {
        if (activity != null) {
            AdManager.showRewardedAd(
                activity = activity,
                onRewardEarned = {
                    val currentStyled = fontVariants.find { it.name == fontStyleName }?.transform?.invoke(inputText) ?: inputText
                    val bmp = renderFontCardBitmap(
                        text = currentStyled,
                        textColor = colorPalettes[selectedColorIndex].second,
                        bgColors = backgroundGradients[selectedBgIndex].second,
                        fontSize = fontSizeSp,
                        hasWatermark = false
                    )
                    ImageExportUtils.saveBitmapToGallery(context, bmp, "FontArt_NoWatermark")
                    Toast.makeText(context, "Saved without watermark! Next export will require video ad again.", Toast.LENGTH_LONG).show()
                    hasWatermark = true
                },
                onDismissed = { hasWatermark = true }
            )
        } else {
            val currentStyled = fontVariants.find { it.name == fontStyleName }?.transform?.invoke(inputText) ?: inputText
            val bmp = renderFontCardBitmap(
                text = currentStyled,
                textColor = colorPalettes[selectedColorIndex].second,
                bgColors = backgroundGradients[selectedBgIndex].second,
                fontSize = fontSizeSp,
                hasWatermark = false
            )
            ImageExportUtils.saveBitmapToGallery(context, bmp, "FontArt_NoWatermark")
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text("Fonts Maker & Designer", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Multiple Fancy Fonts & Colour Text Art", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
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
                        text = { Text("Multiple Fonts (${fontVariants.size})", fontWeight = FontWeight.SemiBold) },
                        icon = { Icon(Icons.Filled.TextFields, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Colour Font Art", fontWeight = FontWeight.SemiBold) },
                        icon = { Icon(Icons.Filled.FormatPaint, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Shared Input Field
            Box(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        hasWatermark = true
                    },
                    label = { Text("Enter Your Text / Bio / Message") },
                    placeholder = { Text("Type anything to convert...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        if (inputText.isNotEmpty()) {
                            IconButton(onClick = { inputText = ""; hasWatermark = true }) {
                                Icon(Icons.Filled.TextFields, contentDescription = "Clear")
                            }
                        }
                    }
                )
            }

            if (selectedTab == 0) {
                // TAB 0: Multiple Fonts Generator List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            "Tap 'Copy' to paste anywhere (Instagram Bio, WhatsApp, BGMI, TikTok)",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    items(fontVariants) { variant ->
                        val converted = remember(inputText, variant.id) {
                            if (inputText.isBlank()) variant.sampleText else variant.transform(inputText)
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(variant.name, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = converted,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    IconButton(
                                        onClick = {
                                            ImageExportUtils.copyToClipboard(context, converted, variant.name)
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Icon(Icons.Filled.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(
                                        onClick = {
                                            ImageExportUtils.shareText(context, converted, "Share Styled Font")
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondaryContainer)
                                    ) {
                                        Icon(Icons.Filled.Share, contentDescription = "Share", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.secondary)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // TAB 1: Colour Font & Text Art Designer
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val currentStyled = remember(inputText, fontStyleName) {
                        fontVariants.find { it.name == fontStyleName }?.transform?.invoke(inputText.ifBlank { "OmniTools VIP" }) ?: inputText
                    }

                    // Live Card Preview
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.2f)
                            .shadow(12.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(backgroundGradients[selectedBgIndex].second))
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Surface(
                                    color = Color.Black.copy(alpha = 0.35f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        fontStyleName.uppercase(),
                                        color = colorPalettes[selectedColorIndex].second,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }

                                Text(
                                    text = currentStyled,
                                    color = colorPalettes[selectedColorIndex].second,
                                    fontSize = fontSizeSp.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = (fontSizeSp * 1.3f).sp
                                )

                                if (hasWatermark) {
                                    Surface(
                                        color = Color.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            "⚡ Made with OmniTools App",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                } else {
                                    Spacer(Modifier.height(12.dp))
                                }
                            }
                        }
                    }

                    // Watermark Notice & Unlock Bar
                    WatermarkControlBar(
                        hasWatermark = hasWatermark,
                        onWatchAdToSaveClean = { saveCleanCardViaAd() }
                    )

                    // Primary Save: Remove Watermark & Save (Rewarded Video Ad)
                    Button(
                        onClick = { saveCleanCardViaAd() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.OndemandVideo, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Remove Watermark & Save HD (Watch Video 🎁)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    // Free Save and Share
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = {
                                val bmp = renderFontCardBitmap(
                                    text = currentStyled,
                                    textColor = colorPalettes[selectedColorIndex].second,
                                    bgColors = backgroundGradients[selectedBgIndex].second,
                                    fontSize = fontSizeSp,
                                    hasWatermark = true
                                )
                                ImageExportUtils.saveBitmapToGallery(context, bmp, "FontArt_Free")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Save (Free)")
                        }
                        OutlinedButton(
                            onClick = {
                                val bmp = renderFontCardBitmap(
                                    text = currentStyled,
                                    textColor = colorPalettes[selectedColorIndex].second,
                                    bgColors = backgroundGradients[selectedBgIndex].second,
                                    fontSize = fontSizeSp,
                                    hasWatermark = true
                                )
                                ImageExportUtils.shareBitmap(context, bmp, "Colour Font Card - $inputText")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Share Card")
                        }
                    }

                    // Choose Font Style
                    Text("Select Font Typography", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Bold Serif", "Bold Sans", "Italic Serif", "Cursive / Script", "Gothic / Fraktur", "Double Struck", "Circled / Bubble", "Monospace Code").forEach { style ->
                            FilterChip(
                                selected = fontStyleName == style,
                                onClick = { fontStyleName = style; hasWatermark = true },
                                label = { Text(style) }
                            )
                        }
                    }

                    // Text Colour Selector
                    Text("Font Neon Colors", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        colorPalettes.forEachIndexed { index, (name, color) ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = if (selectedColorIndex == index) 3.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedColorIndex = index; hasWatermark = true }
                            )
                        }
                    }

                    // Background Gradient Selector
                    Text("Card Background Gradients", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        backgroundGradients.forEachIndexed { index, (name, colors) ->
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Brush.linearGradient(colors))
                                    .border(
                                        width = if (selectedBgIndex == index) 3.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedBgIndex = index; hasWatermark = true }
                            )
                        }
                    }

                    // Font Size Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Font Size", fontSize = 13.sp)
                            Text("${fontSizeSp.toInt()} sp", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = fontSizeSp,
                            onValueChange = { fontSizeSp = it },
                            valueRange = 16f..46f
                        )
                    }
                }
            }
        }
    }
}

// Bitmap Renderer for Colour Font Art
private fun renderFontCardBitmap(
    text: String,
    textColor: Color,
    bgColors: List<Color>,
    fontSize: Float,
    hasWatermark: Boolean
): Bitmap {
    val width = 1080
    val height = 900
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            bgColors.map { it.toArgb() }.toIntArray(),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Border Frame
    val borderPaint = Paint().apply {
        color = textColor.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 6f
        alpha = 80
    }
    canvas.drawRoundRect(RectF(30f, 30f, width - 30f, height - 30f), 24f, 24f, borderPaint)

    val textPaint = Paint().apply {
        color = textColor.toArgb()
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = fontSize * 2.2f
        isFakeBoldText = true
    }

    // Wrap multi-line text
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var curr = ""
    for (w in words) {
        if ((curr + w).length > 20) {
            lines.add(curr)
            curr = "$w "
        } else curr += "$w "
    }
    if (curr.isNotBlank()) lines.add(curr)

    val lineHeight = fontSize * 2.8f
    val startY = (height / 2f) - ((lines.size - 1) * lineHeight / 2f) + (fontSize * 0.7f)
    lines.forEachIndexed { i, line ->
        canvas.drawText(line.trim(), width / 2f, startY + (i * lineHeight), textPaint)
    }

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
        canvas.drawRoundRect(RectF(width / 2f - 240f, height - 70f, width / 2f + 240f, height - 20f), 16f, 16f, wmBg)
        canvas.drawText("⚡ Made with OmniTools App", width / 2f, height - 36f, wmText)
    }

    return bitmap
}

// =========================================================================
// Unicode Mathematical & Styling Converters (100% Native, 0 Third Party)
// =========================================================================

private fun convertMathSerifBold(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D400 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D41A + (ch - 'a')))
            in '0'..'9' -> sb.append(Character.toChars(0x1D7CE + (ch - '0')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertMathSansBold(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D5D4 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D5EE + (ch - 'a')))
            in '0'..'9' -> sb.append(Character.toChars(0x1D7EC + (ch - '0')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertMathSerifItalic(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            'h' -> sb.append("ℎ")
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D434 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D44E + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertMathBoldItalic(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D468 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D482 + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertScript(text: String): String {
    val scriptMap = mapOf(
        'A' to "𝒜", 'B' to "ℬ", 'C' to "𝒞", 'D' to "𝒟", 'E' to "ℰ", 'F' to "ℱ", 'G' to "𝒢",
        'H' to "ℋ", 'I' to "ℐ", 'J' to "𝒥", 'K' to "𝒦", 'L' to "ℒ", 'M' to "ℳ", 'N' to "𝒩",
        'O' to "𝒪", 'P' to "𝒫", 'Q' to "𝒬", 'R' to "ℛ", 'S' to "𝒮", 'T' to "𝒯", 'U' to "𝒰",
        'V' to "𝒱", 'W' to "𝒲", 'X' to "𝒳", 'Y' to "𝒴", 'Z' to "𝒵",
        'a' to "𝒶", 'b' to "𝒷", 'c' to "𝒸", 'd' to "𝒹", 'e' to "ℯ", 'f' to "𝒻", 'g' to "ℊ",
        'h' to "𝒽", 'i' to "𝒾", 'j' to "𝒿", 'k' to "𝓀", 'l' to "𝓁", 'm' to "𝓂", 'n' to "𝓃",
        'o' to "ℴ", 'p' to "𝓅", 'q' to "𝓆", 'r' to "𝓇", 's' to "𝓈", 't' to "𝓉", 'u' to "𝓊",
        'v' to "𝓋", 'w' to "𝓌", 'x' to "𝓍", 'y' to "𝓎", 'z' to "𝓏"
    )
    return text.map { scriptMap[it] ?: it.toString() }.joinToString("")
}

private fun convertBoldScript(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D4D0 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D4EA + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertFraktur(text: String): String {
    val frakturMap = mapOf(
        'C' to "ℭ", 'H' to "ℌ", 'I' to "ℑ", 'R' to "ℜ", 'Z' to "ℨ"
    )
    val sb = StringBuilder()
    for (ch in text) {
        if (frakturMap.containsKey(ch)) {
            sb.append(frakturMap[ch])
        } else {
            when (ch) {
                in 'A'..'Z' -> sb.append(Character.toChars(0x1D504 + (ch - 'A')))
                in 'a'..'z' -> sb.append(Character.toChars(0x1D51E + (ch - 'a')))
                else -> sb.append(ch)
            }
        }
    }
    return sb.toString()
}

private fun convertBoldFraktur(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D56C + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D586 + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertDoubleStruck(text: String): String {
    val map = mapOf(
        'C' to "ℂ", 'H' to "ℍ", 'N' to "ℕ", 'P' to "ℙ", 'Q' to "ℚ", 'R' to "ℝ", 'Z' to "ℤ"
    )
    val sb = StringBuilder()
    for (ch in text) {
        if (map.containsKey(ch)) {
            sb.append(map[ch])
        } else {
            when (ch) {
                in 'A'..'Z' -> sb.append(Character.toChars(0x1D538 + (ch - 'A')))
                in 'a'..'z' -> sb.append(Character.toChars(0x1D552 + (ch - 'a')))
                in '0'..'9' -> sb.append(Character.toChars(0x1D7D8 + (ch - '0')))
                else -> sb.append(ch)
            }
        }
    }
    return sb.toString()
}

private fun convertCircled(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x24B6 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x24D0 + (ch - 'a')))
            in '1'..'9' -> sb.append(Character.toChars(0x2460 + (ch - '1')))
            '0' -> sb.append("⓪")
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertCircledFilled(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1F150 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1F150 + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertSquared(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1F130 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1F130 + (ch - 'a')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertMonospace(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in 'A'..'Z' -> sb.append(Character.toChars(0x1D670 + (ch - 'A')))
            in 'a'..'z' -> sb.append(Character.toChars(0x1D68A + (ch - 'a')))
            in '0'..'9' -> sb.append(Character.toChars(0x1D7F6 + (ch - '0')))
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun convertSmallCaps(text: String): String {
    val map = mapOf(
        'a' to "ᴀ", 'b' to "ʙ", 'c' to "ᴄ", 'd' to "ᴅ", 'e' to "ᴇ", 'f' to "ꜰ", 'g' to "ɢ",
        'h' to "ʜ", 'i' to "ɪ", 'j' to "ᴊ", 'k' to "ᴋ", 'l' to "ʟ", 'm' to "ᴍ", 'n' to "ɴ",
        'o' to "ᴏ", 'p' to "ᴘ", 'q' to "ǫ", 'r' to "ʀ", 's' to "ꜱ", 't' to "ᴛ", 'u' to "ᴜ",
        'v' to "ᴠ", 'w' to "ᴡ", 'x' to "x", 'y' to "ʏ", 'z' to "ᴢ",
        'A' to "ᴀ", 'B' to "ʙ", 'C' to "ᴄ", 'D' to "ᴅ", 'E' to "ᴇ", 'F' to "ꜰ", 'G' to "ɢ",
        'H' to "ʜ", 'I' to "ɪ", 'J' to "ᴊ", 'K' to "ᴋ", 'L' to "ʟ", 'M' to "ᴍ", 'N' to "ɴ",
        'O' to "ᴏ", 'P' to "ᴘ", 'Q' to "ǫ", 'R' to "ʀ", 'S' to "ꜱ", 'T' to "ᴛ", 'U' to "ᴜ",
        'V' to "ᴠ", 'W' to "ᴡ", 'X' to "x", 'Y' to "ʏ", 'Z' to "ᴢ"
    )
    return text.map { map[it] ?: it.toString() }.joinToString("")
}

private fun convertUpsideDown(text: String): String {
    val normal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?!&_."
    val flip = "ɐqɔpǝɟƃɥıɾʞlɯuodbɹsʇnʌʍxʎz∀ᗺƆᗡƎℲ⅁HIſʞ˥WNOԀÒᴚS⊥∩ΛMX⅄Z0ƖᄅƐㄣϛ9ㄥ86¿¡⅋‾˙"
    val sb = StringBuilder()
    for (i in text.length - 1 downTo 0) {
        val c = text[i]
        val idx = normal.indexOf(c)
        if (idx != -1) sb.append(flip[idx]) else sb.append(c)
    }
    return sb.toString()
}

private fun convertStrikethrough(text: String): String =
    text.map { "$it\u0336" }.joinToString("")

private fun convertUnderline(text: String): String =
    text.map { "$it\u0332" }.joinToString("")

private fun convertSlash(text: String): String =
    text.map { "$it\u0337" }.joinToString("")

private fun convertFullwidth(text: String): String {
    val sb = StringBuilder()
    for (ch in text) {
        when (ch) {
            in '!'..'~' -> sb.append(Character.toChars(ch.code + 0xFEE0))
            ' ' -> sb.append("\u3000")
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}
