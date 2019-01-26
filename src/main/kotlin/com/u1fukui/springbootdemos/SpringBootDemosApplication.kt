package com.u1fukui.springbootdemos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootDemosApplication

fun main(args: Array<String>) {
	runApplication<SpringBootDemosApplication>(*args)
}

