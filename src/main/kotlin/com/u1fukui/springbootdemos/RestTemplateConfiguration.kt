package com.u1fukui.springbootdemos

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


@Configuration
class RestTemplateConfiguration {

    @Bean(name = [GITHUB])
    fun githubRestTemplate(): RestTemplate {
        val jsonMessageConverter = MappingJackson2HttpMessageConverter().apply {
            objectMapper = ObjectMapper().apply {
                propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                registerModule(KotlinModule())
            }
        }
        return RestTemplateBuilder()
                .additionalMessageConverters(jsonMessageConverter)
                .build()
    }

    companion object {
        const val GITHUB = "githubRestTemplate"
    }
}