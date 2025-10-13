plugins {
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "shared.llms.config"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koog.agents.core)
            implementation(libs.koog.prompt.executor.llms.all)
            implementation(libs.kotlinx.datetime)
        }
    }
}
