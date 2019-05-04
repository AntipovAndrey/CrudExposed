package com.github.antipovandrey.crudapp.dto.request

import javax.validation.constraints.NotNull

data class DocumentRequest(

        @get:NotNull
        val title: String?,

        @get:NotNull
        val content: String?
)
