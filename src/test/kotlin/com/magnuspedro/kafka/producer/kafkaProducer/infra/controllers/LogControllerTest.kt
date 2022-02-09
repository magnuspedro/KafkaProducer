package com.magnuspedro.kafka.producer.kafkaProducer.infra.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.magnuspedro.kafka.producer.kafkaProducer.entities.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class LogControllerTest {

    private val ENDPOINT: String = "/product"

    private lateinit var mockMvc: MockMvc
    private val mapper: ObjectMapper = ObjectMapper()
    private val kafkaTemplate: KafkaTemplate<String, Any> = mock()
    private lateinit var logController: LogController

    @BeforeEach
    fun setup() {
        logController = LogController("product", kafkaTemplate)
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build()
    }

    @Test
    fun `Should test the endpoint product`() {
        val product = Product(name = "Churrasqueira controle remoto", sku = "5435235")

        mockMvc.post(ENDPOINT) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(product)
        }.andExpect {
            status { isOk() }
        }
    }
}