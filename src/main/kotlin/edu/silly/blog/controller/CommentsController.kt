package edu.silly.blog.controller

import edu.silly.blog.model.Comment
import edu.silly.blog.service.CommentsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
class CommentsController(
    val commentsService: CommentsService
) {
    @GetMapping("/write-comment")
    fun writeComment(
        @RequestParam("article-id") articleId: Long,
        @RequestParam("response-to", required = false) responseTo: Long?,
        model: Model,
    ): String {
        model["article_id"] = articleId
        if (responseTo != null)
            model["response_to"] = responseTo

        return "write-comment"
    }

    @PostMapping("/write-comment")
    fun postComment(
        @RequestParam("article-id") articleId: Long,
        @RequestParam("comment-text") commentText: String,
        @RequestParam("author") author: String, // Name that will be displayed next to the comment
        @RequestParam("response-to", required = false) responseTo: Long?,
    ): String {
        val commentToSave = Comment(
            text = commentText,
            author = author,
            articleId = articleId,
            creationDate = LocalDateTime.now(),
            responseTo = responseTo
        )

        val created = commentsService.save(commentToSave)
        return "redirect:/cringe/${articleId}"
    }
}