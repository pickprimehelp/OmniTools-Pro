package com.example.ui.tools.daily

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.TopBannerAd
import com.example.util.BarcodeQrUtils
import com.example.util.ImageExportUtils
import kotlinx.coroutines.delay
import java.util.Calendar

// ==========================================
// 1. QR CODE & BARCODE GENERATOR
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCodeGeneratorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var qrContent by remember { mutableStateOf("https://google.com") }
    var qrType by remember { mutableStateOf("Website URL") }

    val qrTypes = listOf("Website URL", "WiFi", "UPI Pay", "Contact / Phone", "Plain Text")

    var qrBitmap by remember(qrContent) {
        mutableStateOf(BarcodeQrUtils.generateQrBitmap(qrContent))
    }

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("QR Code Generator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live QR Bitmap Preview
            Card(
                modifier = Modifier
                    .size(240.dp)
                    .shadow(10.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                    qrBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.fillMaxSize()
                        )
                    } ?: CircularProgressIndicator()
                }
            }

            // Export Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        qrBitmap?.let {
                            ImageExportUtils.saveBitmapToGallery(context, it, "QRCode")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Save QR Code")
                }
                OutlinedButton(
                    onClick = {
                        qrBitmap?.let {
                            ImageExportUtils.shareBitmap(context, it, "QR Code: $qrContent")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share QR")
                }
            }

            // Type Chips
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                qrTypes.take(3).forEach { type ->
                    FilterChip(
                        selected = qrType == type,
                        onClick = {
                            qrType = type
                            qrContent = when (type) {
                                "WiFi" -> "WIFI:S:MyHomeWiFi;T:WPA;P:password123;;"
                                "UPI Pay" -> "upi://pay?pa=merchant@upi&pn=Store&am=500"
                                else -> "https://google.com"
                            }
                        },
                        label = { Text(type) }
                    )
                }
            }

            OutlinedTextField(
                value = qrContent,
                onValueChange = { qrContent = it },
                label = { Text("QR Code Content / URL / Text") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ==========================================
// 2. AGE CALCULATOR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var birthYear by remember { mutableIntStateOf(1998) }
    var birthMonth by remember { mutableIntStateOf(5) } // May
    var birthDay by remember { mutableIntStateOf(15) }

    val today = Calendar.getInstance()
    val currentYear = today.get(Calendar.YEAR)
    val currentMonth = today.get(Calendar.MONTH) + 1
    val currentDay = today.get(Calendar.DAY_OF_MONTH)

    var years = currentYear - birthYear
    var months = currentMonth - birthMonth
    var days = currentDay - birthDay

    if (days < 0) {
        months -= 1
        days += 30
    }
    if (months < 0) {
        years -= 1
        months += 12
    }

    val totalDays = (years * 365.25 + months * 30.4 + days).toInt()
    val totalHours = totalDays * 24L

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("Age Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
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
            // Big Age Result Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Your Exact Age Today", fontSize = 13.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "$years Years, $months Months, $days Days",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Input Birth Date
            Text("Enter Your Date of Birth", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = birthDay.toString(),
                    onValueChange = { birthDay = it.toIntOrNull() ?: 1 },
                    label = { Text("Day (1-31)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = birthMonth.toString(),
                    onValueChange = { birthMonth = it.toIntOrNull() ?: 1 },
                    label = { Text("Month (1-12)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = birthYear.toString(),
                    onValueChange = { birthYear = it.toIntOrNull() ?: 1998 },
                    label = { Text("Year (YYYY)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1.2f)
                )
            }

            // Life Statistics Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Life Milestones Lived", fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Days Lived:")
                        Text("${String.format("%,d", totalDays)} Days", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Hours Lived:")
                        Text("${String.format("%,d", totalHours)} Hours", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Heartbeats (approx):")
                        Text("${String.format("%,d", totalDays * 100_000L)} beats", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. BMI CALCULATOR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var heightCmText by remember { mutableStateOf("172") }
    var weightKgText by remember { mutableStateOf("68") }

    val heightCm = heightCmText.toDoubleOrNull() ?: 172.0
    val weightKg = weightKgText.toDoubleOrNull() ?: 68.0

    val heightM = heightCm / 100.0
    val bmi = if (heightM > 0) weightKg / (heightM * heightM) else 0.0

    val (category, catColor, advice) = when {
        bmi < 18.5 -> Triple("Underweight", Color(0xFF3B82F6), "Consider nutrient-dense meals and strength building.")
        bmi < 25.0 -> Triple("Normal Weight", Color(0xFF10B981), "Great job! Keep maintaining a balanced diet and regular exercise.")
        bmi < 30.0 -> Triple("Overweight", Color(0xFFF59E0B), "Incorporate 30 minutes of daily cardio and portion control.")
        else -> Triple("Obesity", Color(0xFFEF4444), "Consult with a health professional for personalized wellness guidance.")
    }

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("BMI Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
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
            // BMI Result Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = catColor.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, catColor)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your Body Mass Index", fontSize = 13.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        String.format("%.1f", bmi),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = catColor
                    )
                    Text(
                        category,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = catColor
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(advice, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            OutlinedTextField(
                value = heightCmText,
                onValueChange = { heightCmText = it },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = weightKgText,
                onValueChange = { weightKgText = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ==========================================
// 4. STOPWATCH & COUNTDOWN TIMER
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchAndTimerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var isRunning by remember { mutableStateOf(false) }
    var elapsedMillis by remember { mutableLongStateOf(0L) }
    val laps = remember { mutableStateListOf<Long>() }

    LaunchedEffect(isRunning) {
        val startTime = System.currentTimeMillis() - elapsedMillis
        while (isRunning) {
            delay(16)
            elapsedMillis = System.currentTimeMillis() - startTime
        }
    }

    val minutes = (elapsedMillis / 60000)
    val seconds = (elapsedMillis % 60000) / 1000
    val millis = (elapsedMillis % 1000) / 10

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("Precision Stopwatch") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            // Stopwatch Display
            Text(
                text = String.format("%02d:%02d.%02d", minutes, seconds, millis),
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary
            )

            // Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { isRunning = !isRunning },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.width(120.dp)
                ) {
                    Icon(if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text(if (isRunning) "Pause" else "Start")
                }

                OutlinedButton(
                    onClick = {
                        if (isRunning) {
                            laps.add(0, elapsedMillis)
                        } else {
                            elapsedMillis = 0L
                            laps.clear()
                        }
                    },
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(if (isRunning) "Lap" else "Reset")
                }
            }

            // Lap List
            if (laps.isNotEmpty()) {
                Text("Recorded Laps (${laps.size})", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    laps.forEachIndexed { index, lapTime ->
                        val m = (lapTime / 60000)
                        val s = (lapTime % 60000) / 1000
                        val ms = (lapTime % 1000) / 10
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Lap #${laps.size - index}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(String.format("%02d:%02d.%02d", m, s, ms), fontFamily = FontFamily.Monospace, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. UNIT CONVERTER SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var inputValueText by remember { mutableStateOf("10") }
    var selectedUnitType by remember { mutableStateOf("Length") }

    val unitTypes = listOf("Length", "Weight", "Temperature", "Speed")
    val inputValue = inputValueText.toDoubleOrNull() ?: 10.0

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("Universal Unit Converter") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
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
            // Unit Type Tabs
            TabRow(selectedTabIndex = unitTypes.indexOf(selectedUnitType)) {
                unitTypes.forEach { type ->
                    Tab(
                        selected = selectedUnitType == type,
                        onClick = { selectedUnitType = type },
                        text = { Text(type) }
                    )
                }
            }

            OutlinedTextField(
                value = inputValueText,
                onValueChange = { inputValueText = it },
                label = { Text("Enter Value") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Converted Values", fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    when (selectedUnitType) {
                        "Length" -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Kilometers (km):")
                                Text(String.format("%.4f km", inputValue / 1000.0), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Centimeters (cm):")
                                Text(String.format("%.1f cm", inputValue * 100.0), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Feet (ft):")
                                Text(String.format("%.2f ft", inputValue * 3.28084), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Inches (in):")
                                Text(String.format("%.2f in", inputValue * 39.3701), fontWeight = FontWeight.SemiBold)
                            }
                        }
                        "Weight" -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Grams (g):")
                                Text(String.format("%.0f g", inputValue * 1000.0), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Pounds (lbs):")
                                Text(String.format("%.2f lbs", inputValue * 2.20462), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Ounces (oz):")
                                Text(String.format("%.2f oz", inputValue * 35.274), fontWeight = FontWeight.SemiBold)
                            }
                        }
                        "Temperature" -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Fahrenheit (°F):")
                                Text(String.format("%.1f °F", (inputValue * 9.0 / 5.0) + 32.0), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Kelvin (K):")
                                Text(String.format("%.2f K", inputValue + 273.15), fontWeight = FontWeight.SemiBold)
                            }
                        }
                        else -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Miles per hour (mph):")
                                Text(String.format("%.2f mph", inputValue * 0.621371), fontWeight = FontWeight.SemiBold)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Meters per sec (m/s):")
                                Text(String.format("%.2f m/s", inputValue / 3.6), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}
