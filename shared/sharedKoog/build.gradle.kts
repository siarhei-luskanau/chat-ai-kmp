plugins {
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "shared.koog"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koog.agents.core)
            implementation(libs.koog.prompt.executor.llms.all)
            implementation(libs.kotlinx.datetime)
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedNetworkApi)
        }
        jvmTest.dependencies {
            implementation(libs.testcontainers.ollama)
            implementation(projects.shared.sharedLlmsContainer)
        }
    }
}
