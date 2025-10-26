plugins {
    id("composeMultiplatformConvention")
    id("androidTestConvention")
}

kotlin.androidLibrary.namespace = "shared.app"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.sharedNavigation)
            implementation(projects.client.sharedUiCommon)
            implementation(projects.client.sharedUiStart)
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedKoog)
            implementation(projects.shared.sharedNetworkApi)
            implementation(projects.shared.sharedNetworkKtor)
        }
    }
}
