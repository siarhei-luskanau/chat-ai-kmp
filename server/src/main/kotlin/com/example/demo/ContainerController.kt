package com.example.demo

import com.example.demo.ContainerConfig.Companion.OLLAMA_EXPOSED_PORT
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.testcontainers.containers.GenericContainer

@RestController
class ContainerController(private val container: GenericContainer<*>) {

    @GetMapping("/container-port")
    fun getContainerPort(): Int = container.getMappedPort(OLLAMA_EXPOSED_PORT)
}
