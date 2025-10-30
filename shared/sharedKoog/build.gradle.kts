import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.buildConfig)
}

kotlin.androidLibrary.namespace = "shared.koog"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koog.agents.core)
            implementation(libs.koog.prompt.executor.llms.all)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(projects.shared.sharedCommon)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.apache5)
        }
        jvmTest.dependencies {
            implementation(libs.slf4j.simple)
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

tasks.withType<Test> {
    systemProperty("project.root.dir", rootDir.absolutePath)
}

buildConfig {
    packageName(kotlin.androidLibrary.namespace.toString())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val llmType = getLlmType { gradleLocalProperties(rootDir, providers) }
    buildConfigField("String", "LLM_TYPE", "\"${llmType}\"")
}
