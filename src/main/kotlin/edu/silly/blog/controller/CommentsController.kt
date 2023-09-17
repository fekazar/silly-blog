package edu.silly.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CommentsController {
    @GetMapping("/write-comment")
    fun writeComment(
        @RequestParam("article-id") articleId: Long,
        model: Model,
    ): String {
        model["article_id"] = articleId
        return "write-comment"
    }
}