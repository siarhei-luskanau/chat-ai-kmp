plugins {
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
    implementation(libs.koog.agents.core)
    implementation(libs.koog.prompt.executor.llms.all)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.spring.boot.starter.web)
    implementation(platform(libs.ktor.bom))
    implementation(projects.shared.sharedLlmsContainer)
    testImplementation(libs.koog.agents.core)
    testImplementation(libs.koog.agents.test)
    testImplementation(libs.koog.prompt.executor.llms.all)
    implementation(projects.shared.sharedLlmsContainer)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.datetime)
    testImplementation(libs.kotlinx.serialization.core)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.client.okhttp)
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
