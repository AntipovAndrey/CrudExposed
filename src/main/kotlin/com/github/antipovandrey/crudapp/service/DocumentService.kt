package com.github.antipovandrey.crudapp.service

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentPreviewResponse
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse

interface DocumentService {

    fun create(request: DocumentRequest): DocumentResponse

    fun findById(id: Int): DocumentResponse?

    fun getAllPreviews(): List<DocumentPreviewResponse>

    fun getAll(): List<DocumentResponse>
}