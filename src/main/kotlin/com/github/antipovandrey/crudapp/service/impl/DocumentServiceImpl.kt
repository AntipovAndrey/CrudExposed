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
import com.github.antipovandrey.crudapp.service.impl.mapping.fillFromRequest
import com.github.antipovandrey.crudapp.service.impl.mapping.toResponse
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
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
        document.tags = Tag.find { Tags.id inList request.tags!! }
        return document.toResponse()
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

    private fun nonNullTags(documentRows: List<DocumentRow>): List<Pair<Int, String>> {
        return documentRows
                .filter { it.tagId != null && it.tagName != null }
                .map { it.tagId!! to it.tagName!! }
    }
}
