package edu.silly.blog.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Article(
    val header: String,
    val body: String,

    @Column(name = "creation_date")
    val creationDate: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
