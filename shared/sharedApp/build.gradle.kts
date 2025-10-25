plugins {
    id("composeMultiplatformConvention")
    id("androidTestConvention")
}

kotlin.androidLibrary.namespace = "shared.app"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedKoog)
            implementation(projects.shared.sharedNavigation)
            implementation(projects.shared.sharedNetworkApi)
            implementation(projects.shared.sharedNetworkKtor)
            implementation(projects.shared.sharedUiCommon)
            implementation(projects.shared.sharedUiStart)
        }
    }
}
