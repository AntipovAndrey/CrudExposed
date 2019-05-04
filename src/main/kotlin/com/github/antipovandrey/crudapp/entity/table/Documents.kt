package com.github.antipovandrey.crudapp.entity.table

import org.jetbrains.exposed.dao.IntIdTable

object Documents : IntIdTable("DOCUMENTS") {

    val title = varchar("title", 40)
    val content = text("content")
}
