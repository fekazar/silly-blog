package edu.silly.blog.model

import jakarta.validation.constraints.Size
import java.beans.ConstructorProperties

data class CreateCommentDto @ConstructorProperties("article-id", "comment-text", "author", "response-to") constructor(
    val articleId: Long,

    @field:Size(max = 1000, message = "Max comment length is 1000 characters.")
    val commentText: String,

    val author: String,
    val responseTo: Long? = null,
)
