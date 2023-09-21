package edu.silly.blog.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Article(
    var header: String? = null,
    var body: String? = null,
    var preview: String? = null,

    @Column(name = "creation_date")
    var creationDate: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    // This cringe for Hibernate to find required ctor
    constructor(header: String?, preview: String?, creationDate: LocalDateTime?, id: Long?) :
            this(header = header, body = null, preview = preview, creationDate = creationDate, id = id)
}
