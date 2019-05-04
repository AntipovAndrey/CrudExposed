package com.github.antipovandrey.crudapp.config

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataBaseConfig(
        @Value("\${crudapp.database.url}") private val url: String,
        @Value("\${crudapp.database.username}") private val userName: String,
        @Value("\${crudapp.database.password}") private val password: String,
        @Value("\${crudapp.database.driver}") private val driverCLassName: String
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        Database.connect(url, driverCLassName, userName, password)
    }
}