package edu.silly.blog.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class ArticleDto(
    val id: Long,
    val header: String,
    val creationDateBacking: LocalDateTime,
    val preview: String? = null,
    val body: String? = null,
) {
    val creationDate
        get() = creationDateBacking
            .minus(18, ChronoUnit.YEARS)
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"))
}
