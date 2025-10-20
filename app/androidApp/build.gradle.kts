plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hot.reload)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.company.app"
    compileSdk = libs.versions.build.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.build.android.minSdk.get().toInt()
        targetSdk = libs.versions.build.android.targetSdk.get().toInt()

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
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())
}

dependencies {
    implementation(libs.androidx.activityCompose)
    implementation(projects.sharedUI)
}
