package shared.koog

import com.github.dockerjava.api.model.Bind
import com.github.dockerjava.api.model.Volume
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.testcontainers.containers.DockerModelRunnerContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.PullPolicy
import org.testcontainers.ollama.OllamaContainer
import shared.common.LlmProfile
import shared.common.toLlmProfile

@Suppress("MagicNumber")
object Containers {
    fun getContainer(): GenericContainer<*>? = when (LLM_TYPE.toLlmProfile()) {
        LlmProfile.OLLAMA_GRANITE -> OllamaContainer("ollama/ollama:latest")
            .apply {
                withExposedPorts(EXPOSED_PORT)
                withCreateContainerCmdModifier { cmd ->
                    cmd.hostConfig?.apply {
                        withMemory(4L * 1024 * 1024 * 1024) // 4GB RAM
                        withCpuCount(2L)
                        val path = System.getProperty("project.root.dir", ".") + "/.ollama"
                        println("Container volume: $path")
                        withBinds(Bind(path, Volume("/root/.ollama")))
                    }
                }
            }
        LlmProfile.DMR_DEEPSEEK -> DockerModelRunnerContainer("alpine/socat:latest")
            .withModel("deepseek-r1-distill-llama:latest")
    }.apply {
        withImagePullPolicy(PullPolicy.alwaysPull())
        withReuse(true)
    }

    fun waitForOllamaServer(baseUrl: String) {
        val httpClient = HttpClient {
            install(HttpTimeout) {
                connectTimeoutMillis = 1000
            }
        }

        val maxAttempts = 100

        runBlocking {
            for (attempt in 1..maxAttempts) {
                @Suppress("TooGenericExceptionCaught")
                try {
                    val response = httpClient.get(baseUrl)
                    if (response.status.isSuccess()) {
                        httpClient.close()
                        return@runBlocking
                    }
                } catch (e: Exception) {
                    if (attempt == maxAttempts) {
                        httpClient.close()
                        throw IllegalStateException(
                            "Ollama server didn't respond after $maxAttempts attemps",
                            e
                        )
                    }
                }
                delay(1000)
            }
        }
    }
    const val EXPOSED_PORT = 11434
}
