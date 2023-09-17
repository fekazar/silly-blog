package edu.silly.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
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

    @PostMapping("/write-comment")
    fun postComment(
        @ModelAttribute("article-id") articleId: Long,
        @ModelAttribute("comment-text") commentText: String,
        @ModelAttribute("author") author: String, // Name that will be displayed next to the comment
    ): String {
        return "redirect:/cringe/${articleId}"
    }
}