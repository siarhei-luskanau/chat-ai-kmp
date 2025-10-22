package com.example.demo

import com.example.LLM_TYPE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.DockerModelRunnerContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.PullPolicy
import org.testcontainers.ollama.OllamaContainer

@Configuration
class ContainerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun container(): GenericContainer<*> = when (LLM_TYPE) {
        "ollama" -> OllamaContainer("ollama/ollama:latest")
            .apply {
                withExposedPorts(OLLAMA_EXPOSED_PORT)
                withCreateContainerCmdModifier { cmd ->
                    cmd.hostConfig?.apply {
                        withMemory(4L * 1024 * 1024 * 1024) // 4GB RAM
                        withCpuCount(2L)
                    }
                }
            }
        "openai" -> DockerModelRunnerContainer("ai/gpt-oss:latest")
            .apply { withExposedPorts(OLLAMA_EXPOSED_PORT) }
        else -> throw IllegalArgumentException("Unexpected LLM_TYPE value: $LLM_TYPE")
    }.apply {
        withImagePullPolicy(PullPolicy.alwaysPull())
        withReuse(false)
    }

    companion object Companion {
        const val OLLAMA_EXPOSED_PORT = 11434
    }
}
