import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import shared.app.KoinApp

fun mainViewController(): UIViewController = ComposeUIViewController {
    KoinApp()
}
