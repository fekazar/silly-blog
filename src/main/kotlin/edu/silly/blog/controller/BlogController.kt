package edu.silly.blog.controller

import edu.silly.blog.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException

@Controller
class BlogController(
    val articleService: ArticleService
) {
    @GetMapping("/cringe")
    fun cringe() = "home"

    // Redirect admin to newly created article
    @PostMapping("/admin/create-article")
    fun createArticle(
        @ModelAttribute("article-header") articleHeader: String,
        @ModelAttribute("article-body") articleBody: String,
        model: Model
    ): String {
        val created = articleService.createArticle(articleHeader, articleBody)
        model["article"] = created.copy(creationDate = created.creationDate.minusYears(18))
        return "redirect:/cringe/${created.id}"
    }

    @GetMapping("/cringe/{id}")
    fun getArticle(
        @PathVariable("id") articleId: Long,
        model: Model
    ): String {
        val article = articleService.getArticle(articleId)
        if (article == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        model["article"] = article
        return "article"
    }
}
