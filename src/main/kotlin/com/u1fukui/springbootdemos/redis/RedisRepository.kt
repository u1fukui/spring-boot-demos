package com.u1fukui.springbootdemos.redis

import com.u1fukui.springbootdemos.dto.RepositorySearchResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRepository {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    fun clearAll() = redisTemplate.apply {
        delete(KEY_COUNT)
        delete(KEY_GITHUB_SEARCH_RESULT)
    }

    fun countUp(): Long = redisTemplate.opsForValue().increment(KEY_COUNT)!!

    fun findSearchResult(query: String): RepositorySearchResult? {
        val key = getSearchResultKey(query)
        return redisTemplate.opsForValue().get(key) as? RepositorySearchResult?
    }

    fun storeSearchResult(query: String, result: RepositorySearchResult) {
        val key = getSearchResultKey(query)
        redisTemplate.opsForValue().set(key, result, 30, TimeUnit.SECONDS)
    }

    private fun getSearchResultKey(query: String) = "$KEY_GITHUB_SEARCH_RESULT::$query"

    companion object {
        private const val KEY_COUNT = "redis_repository:count:v1"
        private const val KEY_GITHUB_SEARCH_RESULT = "redis_repository:github_search_result:v1"
    }
}