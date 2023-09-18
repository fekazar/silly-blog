package edu.silly.blog.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class CreateTokenDto(
    val token: String,
    @field:NotEmpty(message = "Role cannot be empty") val role: String,

    @field:NotEmpty(message = "Description cannot be empty") val description: String,
)
