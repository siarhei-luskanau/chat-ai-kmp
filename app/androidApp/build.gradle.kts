import org.gradle.api.tasks.testing.logging.TestLogEvent

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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
    }
    buildFeatures.compose = true
    packaging.resources.excludes.add("META-INF/**")
    testOptions {
        unitTests {
            all { test: Test ->
                test.testLogging.events = TestLogEvent.entries.toSet()
                test.testLogging.exceptionFormat =
                    org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
        animationsDisabled = true
        managedDevices.localDevices.create("managedVirtualDevice") {
            device = "Pixel 2"
            apiLevel = 35
        }
    }
}

kotlin {
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())
}

dependencies {
    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    implementation(libs.androidx.activityCompose)
    implementation(projects.client.sharedApp)
    implementation(projects.client.sharedNavigation)
}
