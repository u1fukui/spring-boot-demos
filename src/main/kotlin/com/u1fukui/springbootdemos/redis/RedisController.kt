package com.u1fukui.springbootdemos.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class RedisController {

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    @Qualifier(RestTemplateConfiguration.GITHUB)
    lateinit var restTemplate: RestTemplate

    @PostMapping("/redis/count-up")
    fun countUp(): Long? {
        val count = redisTemplate.opsForValue().increment(KEY_COUNT)
        redisTemplate.opsForValue().set(KEY_COUNT, count.toString())
        return count
    }

    @PostMapping("/redis/clear")
    fun clear() {
        redisTemplate.apply {
            delete(KEY_COUNT)
        }
    }

    @GetMapping("/redis/search")
    fun searchGitHubRepository(@RequestParam("q") query: String): SearchRepositoryResponse {
        return restTemplate.getForObject(
                "https://api.github.com/search/repositories?q=$query",
                SearchRepositoryResponse::class.java
        ) ?: throw Exception()
    }

    companion object {
        private const val KEY_COUNT = "count:v1"
    }

    data class GitHubRepository(
            val id: Long,
            val name: String
    )

    data class SearchRepositoryResponse(
            val totalCount: Int,
            val incompleteResults: Boolean,
            val items: List<GitHubRepository>
    )
}