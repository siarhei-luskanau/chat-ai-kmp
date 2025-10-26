package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer
import shared.llms.container.Containers

@Configuration
class ContainerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun container(): GenericContainer<*> = Containers.getContainer()
}
