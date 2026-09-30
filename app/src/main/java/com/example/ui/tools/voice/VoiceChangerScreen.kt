package com.example.ui.tools.voice

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.PlaybackParams
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.ads.TopBannerAd
import kotlinx.coroutines.delay
import java.io.File

data class VoiceFilter(
    val name: String,
    val emoji: String,
    val pitch: Float,
    val speed: Float,
    val description: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceChangerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current

    val filters = listOf(
        VoiceFilter("Normal", "🎙️", 1.0f, 1.0f, "Original voice recording", Color(0xFF6366F1)),
        VoiceFilter("Chipmunk", "🐿️", 1.6f, 1.25f, "Funny high pitch baby squirrel", Color(0xFFF59E0B)),
        VoiceFilter("Deep Monster", "👹", 0.65f, 0.85f, "Terrifying deep cave monster", Color(0xFFEF4444)),
        VoiceFilter("Helium Balloon", "🎈", 1.85f, 1.1f, "Inhaled helium squeaky voice", Color(0xFFEC4899)),
        VoiceFilter("Robot / Sci-Fi", "🤖", 1.2f, 0.95f, "Futuristic cybernetic synth", Color(0xFF06B6D4)),
        VoiceFilter("Slow Motion", "🐢", 0.75f, 0.65f, "Dramatic deep slow speech", Color(0xFF8B5CF6)),
        VoiceFilter("Fast Forward", "⚡", 1.3f, 1.6f, "Ultra high speed playback", Color(0xFF10B981)),
        VoiceFilter("Walkie Talkie", "📻", 1.1f, 1.05f, "Vintage radio frequency", Color(0xFFF97316))
    )

    var selectedFilterIndex by remember { mutableIntStateOf(1) } // Chipmunk by default
    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var recordingDurationSec by remember { mutableIntStateOf(0) }
    var hasRecording by remember { mutableStateOf(false) }

    val audioFile = remember { File(context.cacheDir, "omnitools_voice_temp.mp4") }
    var mediaRecorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Audio recording timer
    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingDurationSec = 0
            while (isRecording) {
                delay(1000)
                recordingDurationSec++
            }
        }
    }

    // Permission launcher
    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasAudioPermission = granted
        if (!granted) {
            Toast.makeText(context, "Audio permission needed to record your voice", Toast.LENGTH_SHORT).show()
        }
    }

    // Safe cleanup on dispose
    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaPlayer?.release()
                mediaRecorder?.release()
            } catch (e: Exception) {
                // ignore
            }
        }
    }

    fun startRecording() {
        if (!hasAudioPermission) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            return
        }
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false

            if (audioFile.exists()) audioFile.delete()

            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile.absolutePath)
                prepare()
                start()
            }
            mediaRecorder = recorder
            isRecording = true
            hasRecording = false
        } catch (e: Exception) {
            Toast.makeText(context, "Error starting recording: ${e.message}", Toast.LENGTH_SHORT).show()
            isRecording = false
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
            hasRecording = audioFile.exists() && audioFile.length() > 0
            Toast.makeText(context, "Recording saved! Select a voice effect to play.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            isRecording = false
        }
    }

    fun playWithFilter(filter: VoiceFilter) {
        if (!audioFile.exists() || audioFile.length() == 0L) {
            Toast.makeText(context, "Please record your voice first!", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            mediaPlayer?.release()
            val player = MediaPlayer().apply {
                setDataSource(audioFile.absolutePath)
                prepare()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val params = PlaybackParams().apply {
                        pitch = filter.pitch
                        speed = filter.speed
                    }
                    playbackParams = params
                }
                setOnCompletionListener {
                    isPlaying = false
                }
                start()
            }
            mediaPlayer = player
            isPlaying = true
        } catch (e: Exception) {
            Toast.makeText(context, "Error playing audio: ${e.message}", Toast.LENGTH_SHORT).show()
            isPlaying = false
        }
    }

    fun stopPlaying() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
        } catch (e: Exception) {
            isPlaying = false
        }
    }

    // Animation for recording pulse
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isRecording || isPlaying) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            Column {
                TopBannerAd()
                TopAppBar(
                    title = { Text("Voice Changer Tools") },
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
            // Hero Mic Studio Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isRecording) "Recording Voice... (${recordingDurationSec}s)"
                        else if (isPlaying) "Playing: ${filters[selectedFilterIndex].name}"
                        else if (hasRecording) "Voice Recorded! Tap a filter below to listen"
                        else "Tap Red Mic to Record Voice",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = if (isRecording) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(16.dp))

                    // Animated Mic Button
                    Box(
                        modifier = Modifier
                            .scale(pulseScale)
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    if (isRecording) listOf(Color(0xFFEF4444), Color(0xFFDC2626))
                                    else if (isPlaying) listOf(Color(0xFF10B981), Color(0xFF059669))
                                    else listOf(Color(0xFF6366F1), Color(0xFF4F46E5))
                                )
                            )
                            .clickable {
                                if (isRecording) stopRecording() else startRecording()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                            contentDescription = "Mic",
                            tint = Color.White,
                            modifier = Modifier.size(42.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Playback Controls
                    if (hasRecording) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = {
                                    if (isPlaying) stopPlaying() else playWithFilter(filters[selectedFilterIndex])
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text(if (isPlaying) "Stop" else "Play Filter")
                            }

                            OutlinedButton(
                                onClick = {
                                    try {
                                        val uri: Uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.provider",
                                            audioFile
                                        )
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "audio/*"
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Share Audio"))
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error sharing audio", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Share Audio")
                            }
                        }
                    }
                }
            }

            // Voice Filter Grid
            Text(
                text = "Select Voice Effect",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.chunked(2).forEach { rowFilters ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowFilters.forEach { filter ->
                            val isSelected = filters.indexOf(filter) == selectedFilterIndex
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        selectedFilterIndex = filters.indexOf(filter)
                                        if (hasRecording) {
                                            playWithFilter(filter)
                                        }
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) filter.color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) filter.color else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(filter.emoji, fontSize = 28.sp)
                                    Spacer(Modifier.height(4.dp))
                                    Text(filter.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(filter.description, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
