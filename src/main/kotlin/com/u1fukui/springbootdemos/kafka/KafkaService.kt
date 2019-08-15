package com.u1fukui.springbootdemos.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback


@Service
class KafkaService {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    fun sendTestMessage(message: String) {
        kafkaTemplate.send(
                KafkaConfiguration.TOPIC_TEST,
                message
        ).addCallback(object : ListenableFutureCallback<SendResult<String, String>> {
            override fun onSuccess(result: SendResult<String, String>?) {
                println("Sent message=[$message] with offset=[${result?.recordMetadata?.offset()}]")
            }

            override fun onFailure(ex: Throwable) {
                println("Unable to send message=[$message] due to : ${ex.message}")
            }
        })
    }
}