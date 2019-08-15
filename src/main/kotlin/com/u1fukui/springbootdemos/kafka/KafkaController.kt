package com.u1fukui.springbootdemos.kafka

import com.u1fukui.springbootdemos.dto.EmptyResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaController {

    @Autowired
    lateinit var kafkaService: KafkaService

    @PostMapping("/kafka/message")
    fun sendMessage(@RequestParam("message") message: String): EmptyResponse {
        kafkaService.sendTestMessage(message)
        return EmptyResponse
    }
}
