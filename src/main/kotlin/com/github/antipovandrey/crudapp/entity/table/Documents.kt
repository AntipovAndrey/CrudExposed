package com.github.antipovandrey.crudapp.entity.table

import org.jetbrains.exposed.dao.IntIdTable

object Documents : IntIdTable() {

    val title = varchar("title", 40)
    val content = text("content")
}
