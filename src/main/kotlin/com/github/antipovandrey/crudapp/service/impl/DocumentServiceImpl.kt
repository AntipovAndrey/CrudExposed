package com.github.antipovandrey.crudapp.service.impl

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentPreviewResponse
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse
import com.github.antipovandrey.crudapp.dto.response.TagResponse
import com.github.antipovandrey.crudapp.entity.Document
import com.github.antipovandrey.crudapp.entity.Tag
import com.github.antipovandrey.crudapp.entity.table.DocumentTags
import com.github.antipovandrey.crudapp.entity.table.Documents
import com.github.antipovandrey.crudapp.entity.table.Tags
import com.github.antipovandrey.crudapp.service.DocumentService
import com.github.antipovandrey.crudapp.service.impl.exceptions.NotFoundException
import com.github.antipovandrey.crudapp.service.impl.mapping.fillFromRequest
import com.github.antipovandrey.crudapp.service.impl.mapping.toResponse
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DocumentServiceImpl : DocumentService {

    data class DocumentRow(val id: Int, val title: String, val tagId: Int?, val tagName: String?)

    @Transactional
    override fun create(request: DocumentRequest): DocumentResponse {
        val document = Document.new {
            fillFromRequest(request)
        }
        document.fillTags(request)
        return document.toResponse()
    }

    @Transactional
    override fun replace(id: Int, request: DocumentRequest): DocumentResponse {
        val document = Document.findById(id) ?: throw NotFoundException("Document $id not found")
        document.fillFromRequest(request)
        document.fillTags(request)
        return document.toResponse()
    }

    @Transactional
    override fun delete(id: Int) {
        DocumentTags.deleteWhere { DocumentTags.document eq id }
        Documents.deleteWhere { Documents.id eq id }
    }

    @Transactional
    override fun findById(id: Int): DocumentResponse? {
        return Document.findById(id)
                ?.load(Document::tags)
                ?.toResponse()
    }

    @Transactional
    override fun getAllPreviews(): List<DocumentPreviewResponse> {
        return queryPreviews { selectAll() }
    }

    @Transactional
    override fun getAll(): List<DocumentResponse> {
        return Document.all()
                .with(Document::tags)
                .map { it.toResponse() }
    }

    @Transactional
    override fun getPreviewsByTagId(id: Int): List<DocumentPreviewResponse> {
        return queryPreviews { select { Tags.id eq id } }
    }

    private inline fun queryPreviews(query: FieldSet.() -> Query): List<DocumentPreviewResponse> {
        val slice: FieldSet = (Documents leftJoin DocumentTags leftJoin Tags)
                .slice(Documents.id, Documents.title, Tags.id, Tags.name)

        return slice.query()
                .asSequence()
                .map {
                    DocumentRow(
                            it[Documents.id].value,
                            it[Documents.title],
                            it.tryGet(Tags.id)?.value, it.tryGet(Tags.name)
                    )
                }
                .groupBy { it.id }
                .values
                .asSequence()
                .map { documentRows -> documentRows.first() to nonNullTags(documentRows) }
                .map { (doc, tags) ->
                    DocumentPreviewResponse(
                            doc.id,
                            doc.title,
                            tags.map { (id, name) -> TagResponse(id, name) }
                    )
                }
                .toList()
    }

    private fun Document.fillTags(request: DocumentRequest) {
        tags = Tag.find { Tags.id inList request.tags!! }
    }

    private fun nonNullTags(documentRows: List<DocumentRow>): List<Pair<Int, String>> {
        return documentRows
                .filter { it.tagId != null && it.tagName != null }
                .map { it.tagId!! to it.tagName!! }
    }
}
