package edu.silly.blog.model

import jakarta.persistence.*
import java.time.LocalDateTime

// There are no any associations by hibernate
// to make it easier to work with immutable structures

@Entity
data class Comment(
    val text: String,
    val author: String,

    @Column(name = "creation_date")
    val creationDate: LocalDateTime,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "response_to")
    val responseTo: Long? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
