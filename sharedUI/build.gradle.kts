plugins {
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "org.company.app"

    sourceSets {
        commonMain.dependencies {
        }

        androidMain.dependencies {
        }

        jvmMain.dependencies {
        }

        iosMain.dependencies {
        }

        webMain.dependencies {
        }
    }
}
