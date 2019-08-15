package com.u1fukui.springbootdemos.`spring-cache`

import com.u1fukui.springbootdemos.`spring-cache`.SpringCache.Companion.GITHUB_SEARCH_RESULT
import com.u1fukui.springbootdemos.dto.RepositorySearchResult
import com.u1fukui.springbootdemos.rest.RestTemplateConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SpringCacheService {

    @Autowired
    @Qualifier(RestTemplateConfig.GITHUB)
    lateinit var restTemplate: RestTemplate

    @Cacheable(
            value = [GITHUB_SEARCH_RESULT],
            key = "#query"
    )
    fun search(query: String): RepositorySearchResult =
            restTemplate.getForObject(
                    "https://api.github.com/search/repositories?q=$query",
                    RepositorySearchResult::class.java
            ) ?: throw Exception()
}