package shared.app

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import shared.navigation.NavApp

@Preview
@Composable
fun KoinApp() = KoinMultiplatformApplication(
    config = KoinConfiguration {
        modules(
            appModule,
            appPlatformModule
        )
    }
) {
    NavApp()
}

expect val appPlatformModule: Module

val appModule by lazy {
    module {}
}
