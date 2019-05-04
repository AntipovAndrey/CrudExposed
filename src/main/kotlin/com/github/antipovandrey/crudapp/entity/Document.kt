package com.github.antipovandrey.crudapp.entity

import com.github.antipovandrey.crudapp.entity.table.Documents
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Document(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Document>(Documents)

    var title by Documents.title
    var content by Documents.content
}
