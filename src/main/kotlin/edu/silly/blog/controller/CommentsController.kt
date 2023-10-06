package edu.silly.blog.controller

import edu.silly.blog.model.Comment
import edu.silly.blog.model.CreateCommentDto
import edu.silly.blog.service.CommentsService
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
@Validated
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
        @Valid toCreate: CreateCommentDto,
    ): String {
        val commentToSave = Comment(
            text = toCreate.commentText,
            author = toCreate.author,
            articleId = toCreate.articleId,
            creationDate = LocalDateTime.now(),
            responseTo = toCreate.responseTo
        )

        val created = commentsService.save(commentToSave)
        return "redirect:/cringe/${toCreate.articleId}"
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun constraintsViolationHandler(ex: MethodArgumentNotValidException, model: Model): String {
        model["violations"] = ex.fieldErrors.map { it.defaultMessage }
        return "errors"
    }
}