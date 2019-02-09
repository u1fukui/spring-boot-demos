package com.u1fukui.springbootdemos.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController {
    @Autowired
    lateinit var redisRepository: RedisRepository

    @PostMapping("/redis/count-up")
    fun countUp(): Long = redisRepository.countUp()

    @PostMapping("/redis/clear")
    fun clear() = redisRepository.clear()

    @GetMapping("/redis/search")
    fun search(@RequestParam("q") query: String): RepositorySearchResult =
            redisRepository.search(query)
}