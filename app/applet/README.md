# 🛠️ OmniTools - All-in-One Android Smart Utility & Creator Suite

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-green.svg)](https://developer.android.com/jetpack/compose)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-35-blue.svg)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](LICENSE)
[![Google Play Ready](https://img.shields.io/badge/Google%20Play-Compliant-brightgreen.svg)](PRIVACY_POLICY.md)

**OmniTools** is a complete, native Android mobile application built with **Kotlin** and **Jetpack Compose (Material 3)**. It packs over 40+ professional daily tools, creators' studios, cards & banner makers, business calculators, audio voice modulators, and photo processors into a single, lightning-fast app.

---

## 🌟 Key Features & Tools

### 1. 🎨 Design & Cards Studio
- **Wedding Card Maker**: Royal Indian & classic wedding invitation cards with custom bride & groom names, venues, dates, and luxury themes.
- **Fonts Maker & Text Art**: 23+ unicode fancy fonts (Bold, Script, Gothic, Bubble, Monospace) with 1-tap copy & share, plus a neon colour font card designer.
- **Status Maker**: Trending WhatsApp & Instagram quote cards with vibrant gradients and typography presets.
- **Invitation Card Maker**: Birthday, Anniversary, Housewarming, and Grand Opening cards.
- **Reels & Story Maker**: 9:16 vertical cover and viral hook title creator.
- **YouTube Thumbnail Maker**: 16:9 high-CTR thumbnail editor with action badges and safe-zone overlay.

### 2. 🎙️ Voice & Audio Studio (100% Native)
- **Voice Changer**: Record audio locally and apply funny/cool filters (Robot, Helium/Chipmunk, Deep Giant, Fast Forward, Slow Motion, Walkie-Talkie).
- **Audio Export & Share**: Save voice clips and share directly to WhatsApp.

### 3. 💼 Business & Finance Tools
- **Invoice & Bill Generator**: Professional tax invoices with automatic CGST, SGST & IGST calculations, itemized lists, and printable export.
- **GST Calculator**: Instant inclusive & exclusive GST computations (5%, 12%, 18%, 28%).
- **EMI & Loan Calculator**: Monthly installment planner with interest vs. principal breakdown.
- **Profit & Margin Calculator**: Selling price, markup, and discount analyzer.

### 4. 📸 Image & Document Processors
- **Image Compressor**: Reduce MBs to KBs without visible quality loss.
- **Passport Photo Maker**: 35x45mm / 2x2 inch standard passport photos with studio backgrounds and 6-photo print sheets.
- **JPG ↔ PNG Converter**: High-fidelity format conversion.
- **Text to PDF & Word Counter**: Create PDF documents and count characters/words in real-time.

### 5. ⚡ Daily Utilities
- **QR Code & Barcode Generator**: For URLs, Text, WiFi, UPI Payments, and Code 128 barcodes.
- **Age & Birthday Calculator**: Exact age, next birthday countdown, total hours and minutes lived.
- **BMI & Health Tracker**: Body Mass Index calculator with health category indicator.
- **Stopwatch & Timer**: Precision millisecond stopwatch with lap support.

---

## 🎁 Smart Monetization & Watermark System
- **App Promotion Watermark**: Cards and images generated in free mode include an elegant `"⚡ Made with OmniTools App"` badge.
- **Rewarded Ad Watermark Removal**: Users can remove the watermark for individual exports by watching an official **Google AdMob Rewarded Video Ad**.
- **AdMob Integration**: Google AdMob Banner, Interstitial, and Rewarded Ads implemented following Google Play Better Ads Policies.
- **Developer Donation QR**: Embedded UPI QR code support for Google Pay, PhonePe, Paytm, and BHIM in the app footer.

---

## 🛡️ Privacy & Google Play Policy Compliance
- **Zero Cloud Uploads**: 100% of image rendering, voice recording, calculations, and PDF generation happen **locally on the user's device**.
- **Least-Privilege Permissions**: Only `RECORD_AUDIO` (for voice changer) and `INTERNET` (for AdMob) are used.
- **Full Privacy Policy**: Detailed in [`PRIVACY_POLICY.md`](PRIVACY_POLICY.md) and viewable directly inside the app.

---

## 📱 Tech Stack & Architecture
- **Language**: Kotlin 2.0.0
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Architecture**: Single Activity, Clean Architecture, Reactive State Management
- **Graphics & Export**: Android Canvas & Bitmap MediaStore API
- **Audio Engine**: Native `MediaRecorder`, `MediaPlayer`, and `PlaybackParams`
- **Ads SDK**: Google Mobile Ads SDK (`play-services-ads`)

---

## 🚀 Building & Exporting

### Open in Android Studio
1. Clone or import the repository:
   ```bash
   git clone https://github.com/your-username/OmniTools-Android.git
   ```
2. Open the project folder in **Android Studio Meerkat / Ladybug / Koala**.
3. Let Gradle sync and run on any Android device running Android 7.0+ (API 24+).

### Generating Release APK or AAB
```bash
./gradlew bundleRelease  # Generates .aab for Google Play Console
./gradlew assembleRelease # Generates release .apk
```

---

## 👨‍💻 Developer & Contact
- **Developer**: Rambeer Kashyap
- **Email**: rambeerkashyap76@gmail.com
- **UPI Support**: rambeerkashyap76@okhdfcbank
