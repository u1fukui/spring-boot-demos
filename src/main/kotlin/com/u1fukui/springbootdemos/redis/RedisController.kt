package com.u1fukui.springbootdemos.redis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController {

    @GetMapping("/redis/hello")
    fun hello(): String = "Hello World"
}