package com.u1fukui.springbootdemos.cache

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
@EnableCaching
class SpringCacheConfiguration {

    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @Autowired
    lateinit var serializer: RedisSerializer<Any>

    @Bean
    fun cacheManager(): CacheManager =
            RedisCacheManager.builder(redisConnectionFactory)
                    .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                            .serializeValuesWith(SerializationPair.fromSerializer<Any>(serializer)))
                    .build()
}