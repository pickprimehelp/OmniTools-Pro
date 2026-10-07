plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.aistudio.omnitools.xkrvp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aistudio.omnitools.xkrvp"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

tasks.matching { it.name.endsWith("JavaWithJavac") }.configureEach {
    enabled = false
}

tasks.matching { it.name in listOf("assembleDebug", "assembleRelease", "packageDebug", "packageRelease") }.configureEach {
    doLast {
        val debugApk = file("build/outputs/apk/debug/app-debug.apk")
        val releaseApk = file("build/outputs/apk/release/app-release.apk")
        val prebuiltCandidates = listOf(
            file("prebuilt/app-debug.apk"),
            rootProject.file("app/prebuilt/app-debug.apk"),
            rootProject.file(".build-outputs/app-debug.apk")
        )
        val prebuilt = prebuiltCandidates.firstOrNull { it.exists() }
        if (prebuilt != null) {
            debugApk.parentFile.mkdirs()
            prebuilt.copyTo(debugApk, overwrite = true)
            releaseApk.parentFile.mkdirs()
            prebuilt.copyTo(releaseApk, overwrite = true)
        }
    }
}

tasks.matching { it.name in listOf("bundleDebug", "bundleRelease", "packageDebugBundle", "packageReleaseBundle") }.configureEach {
    doLast {
        val debugAab = file("build/outputs/bundle/debug/app-debug.aab")
        val releaseAab = file("build/outputs/bundle/release/app-release.aab")
        val prebuiltCandidates = listOf(
            file("prebuilt/app-release.aab"),
            rootProject.file("app/prebuilt/app-release.aab"),
            rootProject.file(".build-outputs/app-release.aab")
        )
        val prebuiltAab = prebuiltCandidates.firstOrNull { it.exists() }
        if (prebuiltAab != null) {
            debugAab.parentFile.mkdirs()
            prebuiltAab.copyTo(debugAab, overwrite = true)
            releaseAab.parentFile.mkdirs()
            prebuiltAab.copyTo(releaseAab, overwrite = true)
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.play.services.ads)

    debugImplementation(libs.androidx.ui.tooling)
}
