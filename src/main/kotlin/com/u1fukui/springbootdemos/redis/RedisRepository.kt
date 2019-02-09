package com.u1fukui.springbootdemos.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.client.RestTemplate

open class RedisRepository(
        private val redisTemplate: RedisTemplate<String, Any>,
        private val restTemplate: RestTemplate
) {
    fun clear() = redisTemplate.apply {
        delete(KEY_COUNT)
        delete(KEY_GITHUB_SEARCH_RESULT)
    }

    fun countUp(): Long = redisTemplate.opsForValue().increment(KEY_COUNT)!!

    fun searchWithRedisTemplate(query: String): RepositorySearchResult {
        val key = "$KEY_GITHUB_SEARCH_RESULT::$query"
        val cache = redisTemplate.opsForValue().get(key) as? RepositorySearchResult?
        if (cache != null) {
            return cache
        }
        return searchGitHubRepository(query).also {
            redisTemplate.opsForValue().set(key, it)
        }
    }

    private fun searchGitHubRepository(query: String): RepositorySearchResult =
            restTemplate.getForObject(
                    "https://api.github.com/search/repositories?q=$query",
                    RepositorySearchResult::class.java
            ) ?: throw Exception()


    companion object {
        private const val KEY_COUNT = "count:v1"
        private const val KEY_GITHUB_SEARCH_RESULT = "github_search_result:v1"
    }
}