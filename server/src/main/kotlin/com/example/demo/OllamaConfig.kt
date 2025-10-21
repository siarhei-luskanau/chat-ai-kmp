package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.images.PullPolicy
import org.testcontainers.ollama.OllamaContainer

@Configuration
class OllamaConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun ollamaContainer(): OllamaContainer = OllamaContainer("ollama/ollama:latest").apply {
        withExposedPorts(11434)
        withImagePullPolicy(PullPolicy.alwaysPull())
        withCreateContainerCmdModifier { cmd ->
            cmd.hostConfig?.apply {
                withMemory(4L * 1024 * 1024 * 1024) // 4GB RAM
                withCpuCount(2L)
            }
        }
        withReuse(false)
    }
}
