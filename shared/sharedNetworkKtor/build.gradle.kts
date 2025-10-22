import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "shared.network.ktor"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedNetworkApi)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.apache5)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        webMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

buildConfig {
    packageName(kotlin.androidLibrary.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val serverDomain = getServerDomain { gradleLocalProperties(rootDir, providers) }
    buildConfigField("String", "SERVER_DOMAIN", "\"$serverDomain\"")
}
