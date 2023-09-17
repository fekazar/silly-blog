package edu.silly.blog.service

import edu.silly.blog.model.Comment
import edu.silly.blog.repository.CommentsRepository
import org.springframework.stereotype.Service

@Service
class CommentsService(val commentsRepository: CommentsRepository) {
    fun getArticleComments(articleId: Long) = commentsRepository.findAllByArticleId(articleId)
    fun save(comment: Comment) = commentsRepository.save(comment)
}