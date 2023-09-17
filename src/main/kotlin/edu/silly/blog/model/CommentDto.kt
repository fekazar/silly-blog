package edu.silly.blog.model

import java.time.LocalDateTime

// TODO: subtract 18 years from local date
// in init block

data class CommentDto(
    val id: Long,
    val text: String,
    val author: String,
    val creationDate: LocalDateTime, // make it look better
    var depth: Int, // consider making it val
) {
    val indent get() = 1..depth
}