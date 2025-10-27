package shared.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.company.shared.ui.start.StartViewModel
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import shared.koog.KoogService
import shared.navigation.NavApp
import shared.ui.common.theme.AppTheme

@Preview
@Composable
fun KoinApp(onThemeChanged: @Composable (isDark: Boolean) -> Unit = {}) = AppTheme(onThemeChanged) {
    KoinMultiplatformApplication(
        config = KoinConfiguration {
            modules(
                module {
                    factory { StartViewModel(koogService = get()) }
                    factory { KoogService() }
                },
                appPlatformModule
            )
        }
    ) {
        NavApp()
    }
}

expect val appPlatformModule: Module
