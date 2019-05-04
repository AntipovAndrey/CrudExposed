package com.github.antipovandrey.crudapp.service.impl.mapping

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse
import com.github.antipovandrey.crudapp.entity.Document

fun Document.toResponse() = DocumentResponse(id.value, title, content)

fun Document.fillFromRequest(request: DocumentRequest) {
    title = request.title!!
    content = request.content!!
}
