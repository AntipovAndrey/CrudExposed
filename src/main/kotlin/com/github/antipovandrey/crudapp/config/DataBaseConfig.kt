package com.github.antipovandrey.crudapp.config

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataBaseConfig(
        @Value("\${crudapp.database.url}") private val url: String,
        @Value("\${crudapp.database.username}") private val userName: String,
        @Value("\${crudapp.database.password}") private val password: String,
        @Value("\${crudapp.database.driver}") private val driverCLassName: String
) {

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverCLassName)
                .username(userName)
                .password(password)
                .build()
    }

    @Bean
    fun springExposedTransactionManager(dataSource: DataSource): SpringTransactionManager {
        return SpringTransactionManager(dataSource)
    }
}
