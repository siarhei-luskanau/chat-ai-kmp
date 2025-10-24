package com.example.demo

import com.example.demo.OllamaConfig.Companion.OLLAMA_EXPOSED_PORT
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.testcontainers.ollama.OllamaContainer

@RestController
class OllamaController(private val ollamaContainer: OllamaContainer) {

    @GetMapping("/ollama-port")
    fun getOllamaPort(): Int = ollamaContainer.getMappedPort(OLLAMA_EXPOSED_PORT)
}
