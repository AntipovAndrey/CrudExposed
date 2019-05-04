package com.github.antipovandrey.crudapp.controller

import com.github.antipovandrey.crudapp.dto.request.TagRequest
import com.github.antipovandrey.crudapp.dto.response.TagResponse
import com.github.antipovandrey.crudapp.service.TagService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("tag")
@RestController
class TagController(
        private val tagService: TagService
) {

    @GetMapping
    fun getAll(): List<TagResponse> = tagService.getAll()

    @PostMapping
    fun createTag(@Valid @RequestBody request: TagRequest): TagResponse = tagService.create(request)
}