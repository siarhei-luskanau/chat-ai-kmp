plugins {
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "org.company.shared.ui.start"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.sharedUiCommon)
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedKoog)
        }
    }
}
