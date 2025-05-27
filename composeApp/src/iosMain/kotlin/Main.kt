import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import siarhei.luskanau.ai.chat.App

fun mainViewController(): UIViewController = ComposeUIViewController { App() }
