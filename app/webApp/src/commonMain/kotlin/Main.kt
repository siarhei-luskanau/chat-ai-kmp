import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import shared.app.KoinApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() = ComposeViewport { KoinApp() }
