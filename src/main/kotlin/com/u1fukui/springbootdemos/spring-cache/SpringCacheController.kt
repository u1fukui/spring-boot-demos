package com.u1fukui.springbootdemos.`spring-cache`

import com.u1fukui.springbootdemos.dto.RepositorySearchResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpringCacheController {
    @Autowired
    lateinit var springCacheService: SpringCacheService

    @GetMapping("/spring-cache/search")
    fun search(@RequestParam("q") query: String): RepositorySearchResult =
            springCacheService.search(query)
}