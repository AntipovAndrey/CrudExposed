package com.github.antipovandrey.crudapp.dto.request

import javax.validation.constraints.NotNull

data class TagRequest(

        @get:NotNull
        val name: String?
)
