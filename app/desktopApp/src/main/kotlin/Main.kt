import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import shared.app.KoinApp
import shared.llms.container.Containers
import shared.llms.container.Containers.EXPOSED_PORT

fun main() {
    val container = Containers.getContainer()
    container.start()
    val port = container.getMappedPort(EXPOSED_PORT)
    val baseContainerUrl = "http://localhost:$port/"
    application {
        Window(
            title = "Multiplatform App",
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            onCloseRequest = ::exitApplication
        ) {
            @Suppress("MagicNumber")
            window.minimumSize = Dimension(350, 600)
            KoinApp(baseContainerUrl = baseContainerUrl)
        }
    }
}
