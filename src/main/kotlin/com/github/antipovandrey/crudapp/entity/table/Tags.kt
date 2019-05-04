package com.github.antipovandrey.crudapp.entity.table

import org.jetbrains.exposed.dao.IntIdTable

object Tags : IntIdTable() {

    val name = varchar("name", 20)
}