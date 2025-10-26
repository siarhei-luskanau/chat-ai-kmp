import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.testcontainers.ollama)
    implementation(project.dependencies.platform(libs.ktor.bom))
}

buildConfig {
    packageName("shared.llms.container")
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val llmType = getLlmType { gradleLocalProperties(rootDir, providers) }
    buildConfigField("String", "LLM_TYPE", "\"${llmType.llmName}\"")
}
