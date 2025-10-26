package shared.navigation

import androidx.navigation3.runtime.NavKey

class AppNavigation(private val backStack: MutableList<NavKey>) {

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
