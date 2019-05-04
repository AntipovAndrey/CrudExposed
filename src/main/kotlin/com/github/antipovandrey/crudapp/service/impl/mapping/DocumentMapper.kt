package com.github.antipovandrey.crudapp.service.impl.mapping

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse
import com.github.antipovandrey.crudapp.entity.Document

fun Document.toResponse() = DocumentResponse(id.value, title, content)

fun DocumentRequest.fillEntity(document: Document) {
    document.title = title!!
    document.content = content!!
}
