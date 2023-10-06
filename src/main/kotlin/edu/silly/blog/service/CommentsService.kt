package edu.silly.blog.service

import edu.silly.blog.model.Comment
import edu.silly.blog.repository.CommentsRepository
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger

@Service
class CommentsService(
    val commentsRepository: CommentsRepository,
    val mailSender: MailSender,
    val templateMessage: SimpleMailMessage,
) {
    private val log = Logger.getLogger(CommentsService::class.java.toString())

    fun getArticleComments(articleId: Long) = commentsRepository.findAllByArticleId(articleId)

    fun save(comment: Comment): Comment {
        notifyEmail(comment.articleId)
        return commentsRepository.save(comment)
    }

    @Async
    private fun notifyEmail(articleId: Long) {
        try {
            val toSend = SimpleMailMessage(templateMessage).apply {
                subject = "Новый комментарий"
                text = "Появлися новый комментарий к посту $articleId"
            }

            mailSender.send(toSend)
        } catch (mailEx: MailException) {
            log.log(Level.WARNING, mailEx.message)
        }
    }
}
