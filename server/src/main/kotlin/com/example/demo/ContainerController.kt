package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.testcontainers.containers.GenericContainer
import shared.llms.container.Containers.EXPOSED_PORT

@RestController
class ContainerController(private val container: GenericContainer<*>) {

    @GetMapping("/container-port")
    fun getContainerPort(): Int = container.getMappedPort(EXPOSED_PORT)
}
