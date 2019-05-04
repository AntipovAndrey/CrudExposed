package com.github.antipovandrey.crudapp.dto.response

data class DocumentResponse(
        val id: Int,
        val title: String,
        val content: String,
        val tags: List<TagResponse>
)
