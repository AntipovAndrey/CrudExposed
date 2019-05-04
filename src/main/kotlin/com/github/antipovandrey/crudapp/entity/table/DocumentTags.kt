package com.github.antipovandrey.crudapp.entity.table

import org.jetbrains.exposed.sql.Table

object DocumentTags : Table() {

    val document = reference("fk_document", Documents).primaryKey(0)
    val tag = reference("fk_tag", Tags).primaryKey(1)
}
