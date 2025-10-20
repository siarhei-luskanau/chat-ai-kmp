plugins {
    id("composeMultiplatformConvention")
    id("androidTestConvention")
}

kotlin.androidLibrary.namespace = "shared.app"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.sharedNavigation)
            implementation(projects.sharedUI)
        }
    }
}
