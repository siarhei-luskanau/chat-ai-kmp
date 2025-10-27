plugins {
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "org.company.shared.ui.start"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.sharedCommon)
            implementation(projects.shared.sharedKoog)
            implementation(projects.shared.sharedUiCommon)
        }
    }
}
