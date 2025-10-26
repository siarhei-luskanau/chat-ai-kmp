import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.springframework.boot)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java.toolchain.languageVersion = JavaLanguageVersion.of(libs.versions.build.jvmTarget.get().toInt())

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.testcontainers.ollama)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

buildConfig {
    packageName("com.example")
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val llmType = getLlmType { gradleLocalProperties(rootDir, providers) }
    buildConfigField("String", "LLM_TYPE", "\"${llmType.llmName}\"")
}
