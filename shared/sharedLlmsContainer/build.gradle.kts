import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.testcontainers.ollama)
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
