plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin.androidLibrary.namespace = "shared.network.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.sharedCommon)
        }
    }
}
