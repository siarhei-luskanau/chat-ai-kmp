package shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.company.app.App
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Preview
@Composable
fun NavApp() {
    val koin = getKoin()
    val navHostController: NavHostController = rememberNavController()
    val appNavigation = AppNavigation(navHostController = navHostController)
    NavHost(navController = navHostController, startDestination = AppRoutes.Start) {
        composable<AppRoutes.Start> {
            App()
        }
    }
}

internal sealed interface AppRoutes {

    @Serializable
    data object Start : AppRoutes
}
