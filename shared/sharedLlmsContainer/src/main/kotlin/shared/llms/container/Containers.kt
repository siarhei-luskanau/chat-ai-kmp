package shared.llms.container

import org.testcontainers.containers.DockerModelRunnerContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.PullPolicy
import org.testcontainers.ollama.OllamaContainer

@Suppress("MagicNumber")
object Containers {
    fun getContainer(): GenericContainer<*> = when (LLM_TYPE) {
        "ollama" -> OllamaContainer("ollama/ollama:latest")
            .apply {
                withExposedPorts(EXPOSED_PORT)
                withCreateContainerCmdModifier { cmd ->
                    cmd.hostConfig?.apply {
                        withMemory(4L * 1024 * 1024 * 1024) // 4GB RAM
                        withCpuCount(2L)
                    }
                }
            }
        "openai" -> DockerModelRunnerContainer("ai/gpt-oss:latest")
            .apply { withExposedPorts(EXPOSED_PORT) }
        else -> throw IllegalArgumentException("Unexpected LLM_TYPE value: $LLM_TYPE")
    }.apply {
        withImagePullPolicy(PullPolicy.alwaysPull())
        withReuse(false)
    }

    const val EXPOSED_PORT = 11434
}
