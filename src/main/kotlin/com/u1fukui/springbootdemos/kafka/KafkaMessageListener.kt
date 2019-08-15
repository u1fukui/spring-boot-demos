package com.u1fukui.springbootdemos.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class KafkaMessageListener {

    @KafkaListener(topics = [KafkaConfig.TOPIC_TEST], groupId = "group1")
    fun listenTestTopic(
            messages: List<String>,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partition: Int,
            @Header(KafkaHeaders.OFFSET) offset: Int
    ) {
        println("Received Message: ${messages.size}. partition: $partition. offset: $offset")
    }
}