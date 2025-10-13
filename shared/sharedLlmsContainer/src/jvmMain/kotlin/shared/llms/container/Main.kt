package shared.llms.container

import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.delay
import org.testcontainers.ollama.OllamaContainer

@OptIn(ExperimentalTime::class)
suspend fun main() {
    val container = OllamaContainer("ollama/ollama:latest").apply {
        withExposedPorts(11434)
        withCreateContainerCmdModifier { cmd ->
            cmd.hostConfig?.apply {
                withMemory(4L * 1024 * 1024 * 1024) // 4GB RAM
                withCpuCount(2L)
            }
        }
    }
    container.start()
    println("${Clock.System.now()} Container is started")
    while (container.isRunning) {
        delay(60.seconds)
        println("${Clock.System.now()} Container is running")
    }
}
