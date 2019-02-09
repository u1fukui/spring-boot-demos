package com.u1fukui.springbootdemos.redis

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.client.RestTemplate

@Configuration
class RedisConfiguration {

    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @Autowired
    @Qualifier(RestTemplateConfiguration.GITHUB)
    lateinit var restTemplate: RestTemplate

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> =
            RedisTemplate<String, Any>().apply {
                setConnectionFactory(redisConnectionFactory)
                keySerializer = StringRedisSerializer()
                valueSerializer = serializer
            }

    @Bean
    fun redisRepository(): RedisRepository =
            RedisRepository(redisTemplate(), restTemplate)

    companion object {
        val serializer = GenericJackson2JsonRedisSerializer(
                ObjectMapper()
                        .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
                        .registerModules(JavaTimeModule())
                        .registerModule(KotlinModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        )
    }
}