package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ToolCategory(val title: String, val subtitle: String, val badgeColor: Color) {
    ALL("All Tools", "Every tool in one place", Color(0xFF6366F1)),
    DESIGN("Design & Cards", "Weddings, Status, Reels & Thumbnails", Color(0xFFEC4899)),
    CREATOR("Creator Tools", "YouTube, SEO, Hashtags & Revenue", Color(0xFFEF4444)),
    BUSINESS("Business & Finance", "Invoices, GST, EMI & Calculations", Color(0xFF10B981)),
    IMAGE("Image Tools", "Compress, Resize, Crop & Passport", Color(0xFF3B82F6)),
    PDF_DOC("PDF & Document", "PDF Tools, Word Counter & Formatters", Color(0xFF8B5CF6)),
    VOICE("Voice & Audio", "Voice Changer & Sound Effects", Color(0xFFF59E0B)),
    DAILY("Daily Utilities", "QR Code, Barcode, Age, BMI & Time", Color(0xFF14B8A6))
}

data class ToolItem(
    val id: String,
    val title: String,
    val description: String,
    val category: ToolCategory,
    val icon: ImageVector,
    val iconColor: Color,
    val isFeatured: Boolean = false,
    val isPopular: Boolean = false
)

object ToolRegistry {
    val allTools: List<ToolItem> = listOf(
        // 1. DESIGN & CARDS
        ToolItem(
            id = "wedding_card",
            title = "Wedding Card Maker",
            description = "Design royal wedding invitation cards with customizable bride & groom names, venues, dates & themes",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.Favorite,
            iconColor = Color(0xFFEC4899),
            isFeatured = true,
            isPopular = true
        ),
        ToolItem(
            id = "fonts_maker",
            title = "Fonts Maker & Text Art",
            description = "Design 23+ multiple fancy fonts, coloured text art cards, copy unicode fonts for Instagram Bio & WhatsApp",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.TextFields,
            iconColor = Color(0xFF8B5CF6),
            isFeatured = true,
            isPopular = true
        ),
        ToolItem(
            id = "status_maker",
            title = "Status Maker Tools",
            description = "Create trending WhatsApp & Instagram status cards with motivational quotes, gradients & aesthetic styles",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.CardGiftcard,
            iconColor = Color(0xFFF43F5E),
            isFeatured = true
        ),
        ToolItem(
            id = "invitation_card",
            title = "Invitation Card Maker",
            description = "Craft Birthday, Anniversary, Housewarming & Event invitations with festive templates & live export",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.Cake,
            iconColor = Color(0xFF8B5CF6)
        ),
        ToolItem(
            id = "reels_maker",
            title = "Reels Maker Tools",
            description = "9:16 vertical story & reel graphics editor with viral hook titles, badges & aesthetic gradients",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.Movie,
            iconColor = Color(0xFFA855F7),
            isFeatured = true
        ),
        ToolItem(
            id = "yt_thumbnail",
            title = "YouTube Thumbnail Maker",
            description = "16:9 high-CTR thumbnail designer with bold headlines, action badges, safe-zone overlays & backgrounds",
            category = ToolCategory.DESIGN,
            icon = Icons.Filled.Videocam,
            iconColor = Color(0xFFEF4444),
            isFeatured = true,
            isPopular = true
        ),

        // 2. CREATOR TOOLS
        ToolItem(
            id = "yt_title_gen",
            title = "YouTube Title Generator",
            description = "Generate catchy, high-CTR titles tailored for your niche and video keywords",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.EditNote,
            iconColor = Color(0xFFEF4444),
            isPopular = true
        ),
        ToolItem(
            id = "yt_desc_gen",
            title = "YouTube Description Generator",
            description = "Generate SEO-optimized descriptions with timestamps, about sections, social links & hashtags",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.Description,
            iconColor = Color(0xFFF97316)
        ),
        ToolItem(
            id = "hashtag_gen",
            title = "Hashtag Generator",
            description = "Find high-performing hashtags for Instagram Reels, YouTube Shorts, and TikTok with 1-tap copy",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.Tag,
            iconColor = Color(0xFF3B82F6),
            isPopular = true
        ),
        ToolItem(
            id = "tag_gen",
            title = "Tag Generator",
            description = "SEO video tags generator with 500-character limit counter and comma-separated export",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.LocalOffer,
            iconColor = Color(0xFF06B6D4)
        ),
        ToolItem(
            id = "thumbnail_size",
            title = "Thumbnail Size Tool",
            description = "1280x720 dimension inspector, aspect ratio guides and timecode safe-zone checker",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.AspectRatio,
            iconColor = Color(0xFF8B5CF6)
        ),
        ToolItem(
            id = "video_file_size",
            title = "Video File Size Calculator",
            description = "Estimate output file size from bitrate, video resolution, duration & frame rate",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.VideoFile,
            iconColor = Color(0xFF10B981)
        ),
        ToolItem(
            id = "yt_revenue_calc",
            title = "YouTube Revenue Calculator",
            description = "Calculate estimated daily, monthly and yearly earnings based on views and CPM",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.MonetizationOn,
            iconColor = Color(0xFF10B981),
            isPopular = true
        ),
        ToolItem(
            id = "cpm_rpm_calc",
            title = "CPM / RPM Calculator",
            description = "Calculate Cost Per Mille (CPM) and Revenue Per Mille (RPM) from total views and income",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.TrendingUp,
            iconColor = Color(0xFF6366F1)
        ),
        ToolItem(
            id = "shorts_title_gen",
            title = "Shorts Title Generator",
            description = "Ultra-short viral titles under 50 characters optimized for the Shorts feed",
            category = ToolCategory.CREATOR,
            icon = Icons.Filled.Subtitles,
            iconColor = Color(0xFFEC4899)
        ),
        ToolItem(
            id = "ig_caption_gen",
            title = "Instagram Caption Generator",
            description = "Generate aesthetic Instagram captions with catchy hooks, body, emojis and call-to-actions",
            category = ToolCategory.CREATOR,
            icon = Icons.AutoMirrored.Filled.Chat,
            iconColor = Color(0xFFD946EF)
        ),

        // 3. BUSINESS & FINANCE TOOLS
        ToolItem(
            id = "invoice_maker",
            title = "Invoice & Bill Maker",
            description = "Professional invoice generator with GST number, multi-product line items, tax breakdown & PDF share",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Receipt,
            iconColor = Color(0xFF10B981),
            isFeatured = true,
            isPopular = true
        ),
        ToolItem(
            id = "gst_calc",
            title = "GST Calculator",
            description = "Add GST or Remove GST (Inclusive/Exclusive) with 3%, 5%, 12%, 18%, 28% CGST & SGST breakdown",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Calculate,
            iconColor = Color(0xFF059669),
            isPopular = true
        ),
        ToolItem(
            id = "profit_margin",
            title = "Profit Margin Calculator",
            description = "Compute gross profit, net profit margin %, markup % from cost and selling price",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.PriceChange,
            iconColor = Color(0xFF2563EB)
        ),
        ToolItem(
            id = "discount_calc",
            title = "Discount Calculator",
            description = "Calculate exact discounted price, total savings and effective percentage off",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Discount,
            iconColor = Color(0xFFF59E0B)
        ),
        ToolItem(
            id = "emi_calc",
            title = "EMI Calculator",
            description = "Calculate monthly loan EMI, total interest payable and amortization breakdown",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.AccountBalance,
            iconColor = Color(0xFF7C3AED),
            isPopular = true
        ),
        ToolItem(
            id = "salary_calc",
            title = "Salary Calculator",
            description = "Calculate In-Hand take-home salary from Gross CTC with PF, HRA and deductions",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Work,
            iconColor = Color(0xFF0D9488)
        ),
        ToolItem(
            id = "loan_calc",
            title = "Loan Calculator",
            description = "Estimate total repayment, interest cost and comparison for personal or home loans",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.AttachMoney,
            iconColor = Color(0xFF16A34A)
        ),
        ToolItem(
            id = "percentage_calc",
            title = "Percentage Calculator",
            description = "4-in-1 percentage tool: What is X% of Y, % change, and fraction percentages",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Percent,
            iconColor = Color(0xFF4F46E5)
        ),
        ToolItem(
            id = "tax_calc",
            title = "Tax Calculator",
            description = "Quick income tax estimate and slab deductions comparison",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.Calculate,
            iconColor = Color(0xFFEA580C)
        ),
        ToolItem(
            id = "tip_calc",
            title = "Tip Calculator",
            description = "Calculate bill tip percentage and split expenses easily among multiple people",
            category = ToolCategory.BUSINESS,
            icon = Icons.Filled.PriceChange,
            iconColor = Color(0xFF0284C7)
        ),

        // 4. IMAGE TOOLS
        ToolItem(
            id = "image_compressor",
            title = "Image Compressor",
            description = "Reduce image file size by up to 90% without visible quality loss, with live KB comparison",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.Compress,
            iconColor = Color(0xFF3B82F6),
            isFeatured = true,
            isPopular = true
        ),
        ToolItem(
            id = "image_resizer",
            title = "Image Resizer",
            description = "Resize photo dimensions by width, height or scale percentage while locking aspect ratio",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.Straighten,
            iconColor = Color(0xFF0284C7)
        ),
        ToolItem(
            id = "format_converter",
            title = "JPG ↔ PNG Converter",
            description = "Convert photos instantly between JPG and PNG formats with high-fidelity encoding",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.Transform,
            iconColor = Color(0xFF059669)
        ),
        ToolItem(
            id = "image_cropper",
            title = "Image Cropper",
            description = "Crop images to standard social aspect ratios (1:1, 4:5, 16:9, 9:16)",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.Crop,
            iconColor = Color(0xFF7C3AED)
        ),
        ToolItem(
            id = "passport_photo",
            title = "Passport Photo Maker",
            description = "Create official 35x45mm / 2x2 inch passport photos with studio background changer & 6-photo print sheet",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.CameraAlt,
            iconColor = Color(0xFF2563EB),
            isFeatured = true
        ),
        ToolItem(
            id = "photo_size_calc",
            title = "Photo Size Converter",
            description = "Convert pixels to inches/cm at 72, 150, 300 DPI for high-precision printing",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.FormatSize,
            iconColor = Color(0xFF0D9488)
        ),
        ToolItem(
            id = "image_to_pdf_quick",
            title = "Image to PDF",
            description = "Convert single or multiple gallery photos into a neat multi-page PDF document",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.PictureAsPdf,
            iconColor = Color(0xFFDC2626)
        ),
        ToolItem(
            id = "blur_image",
            title = "Blur Image",
            description = "Add smooth gaussian blur filter to images with real-time intensity slider",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.BlurOn,
            iconColor = Color(0xFFD97706)
        ),
        ToolItem(
            id = "image_quality",
            title = "Image Quality Checker",
            description = "Analyze image resolution, megapixels, aspect ratio and print compatibility rating",
            category = ToolCategory.IMAGE,
            icon = Icons.Filled.HighQuality,
            iconColor = Color(0xFF4F46E5)
        ),

        // 5. PDF & DOCUMENT
        ToolItem(
            id = "pdf_merger",
            title = "PDF Merger & Splitter",
            description = "Combine multiple PDF documents together or extract selected pages",
            category = ToolCategory.PDF_DOC,
            icon = Icons.Filled.Layers,
            iconColor = Color(0xFF8B5CF6),
            isPopular = true
        ),
        ToolItem(
            id = "text_to_pdf",
            title = "Text → PDF Maker",
            description = "Type or paste formatted text, add titles & headers, and export a clean PDF document",
            category = ToolCategory.PDF_DOC,
            icon = Icons.Filled.PictureAsPdf,
            iconColor = Color(0xFFDC2626)
        ),
        ToolItem(
            id = "word_counter",
            title = "Word Counter",
            description = "Live counts for words, characters (with/without spaces), sentences, paragraphs & reading time",
            category = ToolCategory.PDF_DOC,
            icon = Icons.Filled.TextFields,
            iconColor = Color(0xFF2563EB),
            isPopular = true
        ),
        ToolItem(
            id = "character_counter",
            title = "Character Counter",
            description = "Check character lengths against Twitter (280), SMS (160) and SEO meta title limits",
            category = ToolCategory.PDF_DOC,
            icon = Icons.Filled.EditNote,
            iconColor = Color(0xFF059669)
        ),
        ToolItem(
            id = "case_converter",
            title = "Case Converter",
            description = "Convert text between UPPERCASE, lowercase, Title Case, camelCase, snake_case & kebab-case",
            category = ToolCategory.PDF_DOC,
            icon = Icons.Filled.ChangeCircle,
            iconColor = Color(0xFFF59E0B)
        ),

        // 6. VOICE & AUDIO
        ToolItem(
            id = "voice_changer",
            title = "Voice Changer Tools",
            description = "Record voice and apply hilarious & cool filters: Chipmunk, Deep Monster, Robot, Helium & Slow-Mo",
            category = ToolCategory.VOICE,
            icon = Icons.Filled.RecordVoiceOver,
            iconColor = Color(0xFFF59E0B),
            isFeatured = true,
            isPopular = true
        ),

        // 7. DAILY UTILITIES
        ToolItem(
            id = "qr_generator",
            title = "QR Code Generator",
            description = "Generate custom QR codes for Website URLs, Text, WiFi, UPI Payments & Phone numbers",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.QrCode,
            iconColor = Color(0xFF059669),
            isFeatured = true,
            isPopular = true
        ),
        ToolItem(
            id = "barcode_generator",
            title = "Barcode Generator",
            description = "Generate standard Code 128 barcodes with custom text labels, ready to scan and share",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.CenterFocusStrong,
            iconColor = Color(0xFF2563EB)
        ),
        ToolItem(
            id = "age_calculator",
            title = "Age Calculator",
            description = "Calculate exact age in years, months, days, total hours, minutes and next birthday countdown",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.Cake,
            iconColor = Color(0xFFEC4899),
            isPopular = true
        ),
        ToolItem(
            id = "date_diff_calc",
            title = "Date Difference Calculator",
            description = "Calculate elapsed days, weeks, months and working days between any two dates",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.CalendarMonth,
            iconColor = Color(0xFF8B5CF6)
        ),
        ToolItem(
            id = "unit_converter",
            title = "Unit Converter",
            description = "Universal converter for Length, Weight, Temperature, Area, Speed and Volume",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.Category,
            iconColor = Color(0xFF06B6D4),
            isPopular = true
        ),
        ToolItem(
            id = "currency_converter",
            title = "Currency Converter",
            description = "Quick converter between INR, USD, EUR, GBP, AED, CAD, AUD, JPY and SGD",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.MonetizationOn,
            iconColor = Color(0xFF10B981)
        ),
        ToolItem(
            id = "bmi_calculator",
            title = "BMI Calculator",
            description = "Calculate Body Mass Index, health category, ideal body weight range and fitness recommendations",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.FitnessCenter,
            iconColor = Color(0xFFF97316),
            isPopular = true
        ),
        ToolItem(
            id = "timezone_converter",
            title = "Time Zone Converter",
            description = "World clock comparison across UTC, IST, EST, PST, GMT, JST and GST timezones",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.Language,
            iconColor = Color(0xFF4F46E5)
        ),
        ToolItem(
            id = "stopwatch",
            title = "Stopwatch",
            description = "High precision millisecond stopwatch with lap tracking and split time recordings",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.Timer,
            iconColor = Color(0xFF3B82F6)
        ),
        ToolItem(
            id = "countdown_timer",
            title = "Countdown Timer",
            description = "Customizable countdown timer with circular progress animation and audio alert",
            category = ToolCategory.DAILY,
            icon = Icons.Filled.Alarm,
            iconColor = Color(0xFFF43F5E)
        )
    )
}
