package edu.silly.blog.repository

import edu.silly.blog.model.Comment
import org.springframework.data.repository.Repository

interface CommentsRepository : Repository<Comment, Long> {
    fun save(comment: Comment): Comment
    fun findAllByArticleId(articleId: Long): List<Comment>
}
