plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin.androidLibrary.namespace = "shared.network.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.sharedCommon)
        }
    }
}
