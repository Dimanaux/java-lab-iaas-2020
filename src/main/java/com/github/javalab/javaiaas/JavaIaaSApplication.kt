package com.github.javalab.javaiaas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@SpringBootApplication
@Controller
@RequestMapping(value = ["/", "/index.html", "/index", "/index.txt"])
class JavaIaaSApplication {
    @GetMapping
    @ResponseBody
    fun index() = "Hello, world!"
}

fun main(args: Array<String>) {
    runApplication<JavaIaaSApplication>(*args)
}
