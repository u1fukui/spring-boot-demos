package com.u1fukui.springbootdemos.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class KafkaMessageListener {

    @KafkaListener(topics = [KafkaConfiguration.TOPIC_TEST], groupId = "group1")
    fun listenTestTopic(
            message: String,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partition: Int
    ) {
        println("Received Message: $message from partition: $partition")
    }
}