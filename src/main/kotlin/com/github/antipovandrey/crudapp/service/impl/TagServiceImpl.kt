package com.github.antipovandrey.crudapp.service.impl

import com.github.antipovandrey.crudapp.dto.request.TagRequest
import com.github.antipovandrey.crudapp.dto.response.TagResponse
import com.github.antipovandrey.crudapp.entity.Tag
import com.github.antipovandrey.crudapp.entity.table.Tags
import com.github.antipovandrey.crudapp.service.TagService
import com.github.antipovandrey.crudapp.service.impl.mapping.fillFromRequest
import com.github.antipovandrey.crudapp.service.impl.mapping.toResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceImpl : TagService {

    @Transactional
    override fun create(request: TagRequest): TagResponse {
        return Tag.new { fillFromRequest(request) }.toResponse()
    }

    @Transactional
    override fun findById(id: Int): TagResponse? {
        return Tag.findById(id)?.toResponse()
    }

    @Transactional
    override fun getAll(): List<TagResponse> {
        return Tag.all().map { it.toResponse() }
    }

    @Transactional
    override fun findByName(name: String): List<TagResponse> {
        return Tag.find { Tags.name like "%$name%" }.map { it.toResponse() }
    }
}