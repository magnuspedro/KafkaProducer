package com.magnuspedro.kafka.producer.kafkaProducer.entities.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.magnuspedro.kafka.producer.kafkaProducer.entities.Product
import org.apache.kafka.common.errors.SerializationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ProductSerializerTest {

    private lateinit var productSerializer: ProductSerializer
    private val mapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        productSerializer = ProductSerializer()
    }

    @Test
    fun `Should return a product byteArray`() {
        val product = Product(name = "Churrasqueira controle remoto", sku = "423414315")

        val productByteArray: ByteArray? = productSerializer.serialize("product", product)

        assertContentEquals(productByteArray, mapper.writeValueAsBytes(product))
    }

    @Test
    fun `Should return a SerializationException for null product`() {
        val exception = assertThrows<SerializationException> {
            productSerializer.serialize("product", null)
        }

        assertEquals(exception.message, "Error when serializing Product to ByteArray[]")
    }

}