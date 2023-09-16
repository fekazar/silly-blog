package edu.silly.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {
    @GetMapping("admin/create-article")
    fun createArticle() = "admin/create-article"

    @GetMapping("admin/tokens")
    fun tokens() = "admin/tokens"
}
