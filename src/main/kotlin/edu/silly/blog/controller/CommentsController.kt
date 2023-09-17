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
        model: Model,
    ): String {
        model["article_id"] = articleId
        return "write-comment"
    }

    @PostMapping("/write-comment")
    fun postComment(
        @ModelAttribute("article-id") articleId: Long,
        @ModelAttribute("comment-text") commentText: String,
        @ModelAttribute("author") author: String, // Name that will be displayed next to the comment
    ): String {
        val commentToSave = Comment(
            text = commentText,
            author = author,
            articleId = articleId,
            creationDate = LocalDateTime.now()
        )

        val created = commentsService.save(commentToSave)
        return "redirect:/cringe/${articleId}"
    }
}