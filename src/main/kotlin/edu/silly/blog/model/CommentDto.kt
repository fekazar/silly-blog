package edu.silly.blog.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class CommentDto(
    val id: Long,
    val text: String,
    val author: String,
    val creationDateBacking: LocalDateTime,
    val depth: Int,
) {
    val indent get() = 1..depth
    val creationDate
        get() = creationDateBacking
            .minus(18, ChronoUnit.YEARS)
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
}