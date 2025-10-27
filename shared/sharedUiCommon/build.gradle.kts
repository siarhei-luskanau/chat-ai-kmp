plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin.androidLibrary.namespace = "shared.ui.common"

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "${kotlin.androidLibrary.namespace}.resources"
    generateResClass = always
}
