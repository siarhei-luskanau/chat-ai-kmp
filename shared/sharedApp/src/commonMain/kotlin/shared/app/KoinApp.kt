package shared.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.company.shared.ui.start.StartViewModel
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import shared.common.GenericResult
import shared.koog.KoogService
import shared.navigation.NavApp
import shared.network.api.NetworkService
import shared.network.ktor.NetworkServiceKtor
import shared.ui.common.theme.AppTheme

@Preview
@Composable
fun KoinApp(onThemeChanged: @Composable (isDark: Boolean) -> Unit = {}) = AppTheme(onThemeChanged) {
    KoinMultiplatformApplication(
        config = KoinConfiguration {
            modules(
                appModule,
                appPlatformModule
            )
        }
    ) {
        NavApp()
    }
}

expect val appPlatformModule: Module

val appModule by lazy {
    module {
        factory { StartViewModel(koogService = get()) }
        single<NetworkService> { NetworkServiceKtor() }
        factory {
            val baseUrlProvider: suspend () -> GenericResult<String> = {
                val networkService: NetworkService = get()
                networkService.getLlmUrl()
            }
            KoogService(baseUrlProvider = baseUrlProvider)
        }
    }
}
