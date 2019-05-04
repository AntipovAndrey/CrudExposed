package com.github.antipovandrey.crudapp.service.impl.mapping

import com.github.antipovandrey.crudapp.dto.request.TagRequest
import com.github.antipovandrey.crudapp.dto.response.TagResponse
import com.github.antipovandrey.crudapp.entity.Tag

fun Tag.toResponse() = TagResponse(id.value, name)

fun Tag.fillFromRequest(request: TagRequest) {
    name = request.name!!
}
