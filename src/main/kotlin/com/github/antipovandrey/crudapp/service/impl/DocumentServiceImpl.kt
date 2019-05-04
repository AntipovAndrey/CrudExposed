package com.github.antipovandrey.crudapp.service.impl

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentPreviewResponse
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse
import com.github.antipovandrey.crudapp.entity.Document
import com.github.antipovandrey.crudapp.entity.table.Documents
import com.github.antipovandrey.crudapp.service.DocumentService
import com.github.antipovandrey.crudapp.service.impl.mapping.fillFromRequest
import com.github.antipovandrey.crudapp.service.impl.mapping.toResponse
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DocumentServiceImpl : DocumentService {

    @Transactional
    override fun create(request: DocumentRequest): DocumentResponse {
        return Document.new { fillFromRequest(request) }
                .toResponse()
    }

    @Transactional
    override fun findById(id: Int): DocumentResponse? {
        return Document.findById(id)?.toResponse()
    }

    @Transactional
    override fun getAllPreviews(): List<DocumentPreviewResponse> {
        return Documents.slice(Documents.id, Documents.title)
                .selectAll()
                .map { DocumentPreviewResponse(it[Documents.id].value, it[Documents.title]) }
    }

    @Transactional
    override fun getAll(): List<DocumentResponse> {
        return Document.all()
                .map { it.toResponse() }
    }
}
