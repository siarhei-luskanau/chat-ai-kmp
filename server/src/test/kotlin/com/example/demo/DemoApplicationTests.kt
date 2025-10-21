package com.example.demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.ollama.OllamaContainer

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private lateinit var ollamaContainer: OllamaContainer

    @Test
    fun contextLoads() {
    }

    @Test
    fun `ollama container bean is created and running`() {
        assertNotNull(ollamaContainer)
        assertTrue(ollamaContainer.isCreated)
        assertTrue(ollamaContainer.isRunning)
    }
}
