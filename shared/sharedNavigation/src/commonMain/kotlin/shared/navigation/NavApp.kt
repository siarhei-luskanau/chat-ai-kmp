package shared.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import org.company.shared.ui.start.StartScreen
import org.koin.compose.getKoin

@Preview
@Composable
fun NavApp() {
    val koin = getKoin()
    val backStack = mutableStateListOf<NavKey>(AppRoutes.Start)
    val appNavigation = AppNavigation(backStack = backStack)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppRoutes.Start> {
                StartScreen(viewModelProvider = { koin.get() })
            }
        }
    )
}

internal sealed interface AppRoutes : NavKey {

    @Serializable
    data object Start : AppRoutes
}
