package edu.silly.blog.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Token(
    @Id
    val id: String,
    val role: String,
    val description: String,

    @Column(name = "creation_date")
    val creationDate: LocalDateTime,
)