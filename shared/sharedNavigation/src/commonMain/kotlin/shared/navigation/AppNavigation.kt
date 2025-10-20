package shared.navigation

import androidx.navigation.NavHostController

class AppNavigation(private val navHostController: NavHostController) {

    fun goBack() {
        navHostController.popBackStack()
    }
}
