package com.github.antipovandrey.crudapp.controller

import com.github.antipovandrey.crudapp.dto.request.DocumentRequest
import com.github.antipovandrey.crudapp.dto.response.DocumentPreviewResponse
import com.github.antipovandrey.crudapp.dto.response.DocumentResponse
import com.github.antipovandrey.crudapp.service.DocumentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("documents")
@RestController
class DocumentsController(
        private val documentService: DocumentService
) {

    @GetMapping
    fun getPreviews(): List<DocumentPreviewResponse> = documentService.getAllPreviews()

    @GetMapping("{id:[0-9]*}")
    fun getDocument(@PathVariable id: Int): ResponseEntity<DocumentResponse> {
        val documentResponse = documentService.findById(id)
        return when (documentResponse) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(documentResponse)
        }
    }

    @GetMapping("search")
    fun getPreviewsForTag(
            @RequestParam("tag") tagId: Int
    ): List<DocumentPreviewResponse> = documentService.getPreviewsByTagId(tagId)

    @PostMapping
    fun createDocument(@Valid @RequestBody request: DocumentRequest): DocumentResponse = documentService.create(request)
}
