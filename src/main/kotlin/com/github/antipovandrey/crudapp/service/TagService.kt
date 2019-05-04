package com.github.antipovandrey.crudapp.service

import com.github.antipovandrey.crudapp.dto.request.TagRequest
import com.github.antipovandrey.crudapp.dto.response.TagResponse

interface TagService {

    fun create(request: TagRequest): TagResponse

    fun findById(id: Int): TagResponse?

    fun getAll(): List<TagResponse>

    fun findByName(name: String): List<TagResponse>
}