package com.u1fukui.springbootdemos.redis

import com.u1fukui.springbootdemos.dto.RepositorySearchResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController {
    @Autowired
    lateinit var redisService: RedisService

    @PostMapping("/redis/count-up")
    fun countUp(): Long = redisService.countUp()

    @PostMapping("/redis/clear")
    fun clearAll() = redisService.clearAll()

    @GetMapping("/redis/search")
    fun search(@RequestParam("q") query: String): RepositorySearchResult =
            redisService.search(query)
}