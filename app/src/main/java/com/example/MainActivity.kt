package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ads.AdManager
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.tools.business.EmiCalculatorScreen
import com.example.ui.tools.business.GstCalculatorScreen
import com.example.ui.tools.business.InvoiceBillMakerScreen
import com.example.ui.tools.business.ProfitDiscountCalculatorScreen
import com.example.ui.tools.creator.HashtagAndTagGeneratorScreen
import com.example.ui.tools.creator.VideoFileSizeCalculatorScreen
import com.example.ui.tools.creator.YouTubeRevenueCalculatorScreen
import com.example.ui.tools.creator.YouTubeTitleGeneratorScreen
import com.example.ui.tools.daily.AgeCalculatorScreen
import com.example.ui.tools.daily.BmiCalculatorScreen
import com.example.ui.tools.daily.QrCodeGeneratorScreen
import com.example.ui.tools.daily.StopwatchAndTimerScreen
import com.example.ui.tools.daily.UnitConverterScreen
import com.example.ui.tools.design.InvitationCardMakerScreen
import com.example.ui.tools.design.ReelsMakerScreen
import com.example.ui.tools.design.StatusMakerScreen
import com.example.ui.tools.design.WeddingCardMakerScreen
import com.example.ui.tools.design.YouTubeThumbnailMakerScreen
import com.example.ui.tools.image.ImageCompressorScreen
import com.example.ui.tools.image.ImageQualityCheckerScreen
import com.example.ui.tools.image.PassportPhotoMakerScreen
import com.example.ui.tools.pdf.CaseConverterScreen
import com.example.ui.tools.pdf.TextToPdfScreen
import com.example.ui.tools.pdf.WordAndCharacterCounterScreen
import com.example.ui.tools.voice.VoiceChangerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize AdMob
        AdManager.initialize(applicationContext)

        setContent {
            MyApplicationTheme {
                OmniToolsApp()
            }
        }
    }
}

@Composable
fun OmniToolsApp() {
    var activeToolId by remember { mutableStateOf<String?>(null) }

    if (activeToolId == null) {
        HomeScreen(onSelectTool = { toolId ->
            activeToolId = toolId
        })
    } else {
        val onBack = { activeToolId = null }

        when (activeToolId) {
            // Design Tools
            "wedding_card" -> WeddingCardMakerScreen(onBack = onBack)
            "fonts_maker" -> com.example.ui.tools.fonts.FontsMakerScreen(onBack = onBack)
            "status_maker" -> StatusMakerScreen(onBack = onBack)
            "invitation_card" -> InvitationCardMakerScreen(onBack = onBack)
            "reels_maker" -> ReelsMakerScreen(onBack = onBack)
            "yt_thumbnail" -> YouTubeThumbnailMakerScreen(onBack = onBack)

            // Business & Finance Tools
            "invoice_maker" -> InvoiceBillMakerScreen(onBack = onBack)
            "gst_calc" -> GstCalculatorScreen(onBack = onBack)
            "emi_calc", "loan_calc" -> EmiCalculatorScreen(onBack = onBack)
            "profit_margin", "discount_calc", "percentage_calc", "salary_calc", "tax_calc", "tip_calc" -> ProfitDiscountCalculatorScreen(onBack = onBack)

            // Creator Tools
            "yt_title_gen", "yt_desc_gen", "ig_caption_gen" -> YouTubeTitleGeneratorScreen(isShortsMode = false, onBack = onBack)
            "shorts_title_gen" -> YouTubeTitleGeneratorScreen(isShortsMode = true, onBack = onBack)
            "hashtag_gen" -> HashtagAndTagGeneratorScreen(isTagMode = false, onBack = onBack)
            "tag_gen" -> HashtagAndTagGeneratorScreen(isTagMode = true, onBack = onBack)
            "thumbnail_size", "video_file_size" -> VideoFileSizeCalculatorScreen(onBack = onBack)
            "yt_revenue_calc", "cpm_rpm_calc" -> YouTubeRevenueCalculatorScreen(onBack = onBack)

            // Voice Tools
            "voice_changer" -> VoiceChangerScreen(onBack = onBack)

            // Image Tools
            "image_compressor", "image_resizer", "format_converter", "image_cropper", "blur_image" -> ImageCompressorScreen(onBack = onBack)
            "passport_photo", "photo_size_calc" -> PassportPhotoMakerScreen(onBack = onBack)
            "image_quality" -> ImageQualityCheckerScreen(onBack = onBack)

            // PDF Tools
            "text_to_pdf", "pdf_merger", "image_to_pdf_quick" -> TextToPdfScreen(onBack = onBack)
            "word_counter", "character_counter" -> WordAndCharacterCounterScreen(onBack = onBack)
            "case_converter" -> CaseConverterScreen(onBack = onBack)

            // Daily Tools
            "qr_generator", "barcode_generator" -> QrCodeGeneratorScreen(onBack = onBack)
            "age_calculator", "date_diff_calc" -> AgeCalculatorScreen(onBack = onBack)
            "bmi_calculator" -> BmiCalculatorScreen(onBack = onBack)
            "stopwatch", "countdown_timer" -> StopwatchAndTimerScreen(onBack = onBack)
            "unit_converter", "currency_converter", "timezone_converter" -> UnitConverterScreen(onBack = onBack)

            else -> HomeScreen(onSelectTool = { activeToolId = it })
        }
    }
}
