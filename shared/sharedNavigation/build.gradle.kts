plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin.androidLibrary.namespace = "shared.navigation"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.navigation3.ui)
            implementation(projects.shared.sharedUiStart)
        }
    }
}
