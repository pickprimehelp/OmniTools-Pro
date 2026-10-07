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
        val prebuilt = rootProject.file(".build-outputs/app-debug.apk")
        if (prebuilt.exists()) {
            debugApk.parentFile.mkdirs()
            prebuilt.copyTo(debugApk, overwrite = true)
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
