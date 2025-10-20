import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.kotlin.multiplatform.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

kotlin {
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())

    androidLibrary {
        compileSdk = libs.versions.build.android.compileSdk.get().toInt()
        minSdk = libs.versions.build.android.minSdk.get().toInt()
        androidResources.enable = true
        withHostTestBuilder {}.configure {
            isIncludeAndroidResources = true
            enableCoverage = true
        }
    }

    jvm()

    js {
        browser()
    }

    wasmJs {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(compose.ui)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project.dependencies.platform(libs.koin.bom))
        }

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.androidx.uitest.junit4)
                implementation(libs.androidx.uitest.testManifest)
            }
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

        iosMain.dependencies {
        }

        webMain.dependencies {
        }
    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach { binaries { framework { baseName = "SharedApp" } } }
}

tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}
