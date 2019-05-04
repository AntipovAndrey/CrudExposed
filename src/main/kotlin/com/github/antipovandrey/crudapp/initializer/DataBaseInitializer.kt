package com.github.antipovandrey.crudapp.initializer

import org.jetbrains.exposed.sql.Database
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
@Order(1)
class DataBaseInitializer(
        private val dataSource: DataSource
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        Database.connect(dataSource)
    }
}
