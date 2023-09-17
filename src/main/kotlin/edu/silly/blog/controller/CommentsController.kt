package edu.silly.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CommentsController {
    @GetMapping("/write-comment")
    fun writeComment(
        @RequestParam("article-id") articleId: Long
    ) = "write-comment"
}