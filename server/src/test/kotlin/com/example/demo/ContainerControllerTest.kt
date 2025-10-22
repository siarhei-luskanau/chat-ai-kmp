package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ContainerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test getContainerPort`() {
        mockMvc.perform(get("/container-port"))
            .andExpect(status().isOk)
    }
}
