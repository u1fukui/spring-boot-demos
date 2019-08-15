package com.u1fukui.springbootdemos.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@EnableKafka
@Configuration
class KafkaConfig {

    @Value(value = "\${kafka.bootstrapServers}")
    lateinit var bootstrapServers: String

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val configs = mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
        return DefaultKafkaProducerFactory<String, String>(configs)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val configs = mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(configs)
    }

    @Bean
    fun kafkaTempalte(): KafkaTemplate<String, String> =
            KafkaTemplate<String, String>(producerFactory())

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> =
            ConcurrentKafkaListenerContainerFactory<String, String>().apply {
                consumerFactory = consumerFactory()
                isBatchListener = true
            }

    companion object {
        const val TOPIC_TEST = "test"
    }
}