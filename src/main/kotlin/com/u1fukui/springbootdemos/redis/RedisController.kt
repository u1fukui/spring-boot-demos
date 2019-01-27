package com.u1fukui.springbootdemos.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController {

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @PostMapping("/redis/countup")
    fun count(): Long? {
        val count = redisTemplate.opsForValue().increment(KEY_COUNT)
        redisTemplate.opsForValue().set(KEY_COUNT, count.toString())
        return count
    }

    companion object {
        private const val KEY_COUNT = "count:v1"
    }
}