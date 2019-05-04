package com.github.antipovandrey.crudapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrudApplication

fun main(args: Array<String>) {
    runApplication<CrudApplication>(*args)
}
