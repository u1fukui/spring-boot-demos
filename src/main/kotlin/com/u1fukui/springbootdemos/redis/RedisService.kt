package com.u1fukui.springbootdemos.redis

import com.u1fukui.springbootdemos.rest.RestTemplateConfiguration
import com.u1fukui.springbootdemos.dto.RepositorySearchResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RedisService {

    @Autowired
    @Qualifier(RestTemplateConfiguration.GITHUB)
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var redisRepository: RedisRepository

    fun clearAll() = redisRepository.clearAll()

    fun countUp(): Long = redisRepository.countUp()

    fun search(query: String): RepositorySearchResult {
        val cache = redisRepository.findSearchResult(query)
        if (cache != null) {
            return cache
        }
        return searchGitHubRepository(query).also {
            redisRepository.storeSearchResult(query, it)
        }
    }

    private fun searchGitHubRepository(query: String): RepositorySearchResult =
            restTemplate.getForObject(
                    "https://api.github.com/search/repositories?q=$query",
                    RepositorySearchResult::class.java
            ) ?: throw Exception()
}