plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidLibrary.namespace = "org.company.shared.ui"

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koog.agents.core)
            implementation(libs.koog.prompt.executor.llms.all)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(project.dependencies.platform(libs.ktor.bom))
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.apache5)
        }

        jvmTest.dependencies {
            implementation(libs.junit.jupiter.params)
            implementation(libs.koog.agents.test)
            implementation(libs.testcontainers.ollama)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        webMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}
