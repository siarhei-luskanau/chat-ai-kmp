package com.example.demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private lateinit var container: GenericContainer<*>

    @Test
    fun contextLoads() {
    }

    @Test
    fun `container bean is created and running`() {
        assertNotNull(container)
        assertTrue(container.isCreated)
        assertTrue(container.isRunning)
    }
}
