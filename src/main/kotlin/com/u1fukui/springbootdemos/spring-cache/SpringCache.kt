package com.u1fukui.springbootdemos.`spring-cache`

import java.time.Duration
import java.time.temporal.ChronoUnit

class SpringCache {
    companion object {
        const val GITHUB_SEARCH_RESULT = "spring_cache_service:github_search_result:v1"

        val map = mapOf(
                GITHUB_SEARCH_RESULT to Duration.of(30, ChronoUnit.SECONDS)
        )
    }
}