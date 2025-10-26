plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin.androidLibrary.namespace = "shared.common"
