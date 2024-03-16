package com.cinephilia.payment.kafka

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun <M> syncSend(topic: String, message: M) {
        val future = kafkaTemplate.send(ProducerRecord(topic, message))
        future.get()
    }
}