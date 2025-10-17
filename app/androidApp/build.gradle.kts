plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.company.app"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        applicationId = "org.company.app.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    packaging.resources.excludes.add("META-INF/**")
    testOptions.managedDevices.localDevices.create("managedVirtualDevice") {
        device = "Pixel 2"
        apiLevel = 35
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.androidx.activityCompose)
    implementation(projects.sharedUI)
}
