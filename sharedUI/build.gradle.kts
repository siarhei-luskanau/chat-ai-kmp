plugins {
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "org.company.app"

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koog.agents.core)
            implementation(libs.koog.prompt.executor.llms.all)
            implementation(project.dependencies.platform(libs.ktor.bom))
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
