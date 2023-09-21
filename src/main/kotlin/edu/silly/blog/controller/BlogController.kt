package edu.silly.blog.controller

import edu.silly.blog.model.ArticleDto
import edu.silly.blog.model.Comment
import edu.silly.blog.model.CommentDto
import edu.silly.blog.service.ArticleService
import edu.silly.blog.service.CommentsService
import edu.silly.blog.validation.CorrectHtml
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.logging.Logger
import kotlin.Comparator

@Controller
@Validated
class BlogController(
    val articleService: ArticleService,
    val commentsService: CommentsService,
) {
    // TODO: make better logging in the whole project
    private val log = Logger.getLogger(BlogController::class.qualifiedName)

    @GetMapping(value = ["/cringe", "/", "/home"])
    fun cringe(model: Model): String {
        // TODO: limit wc in preview
        val articles = articleService.getLatestArticles()
            .map { ArticleDto(it.id!!, it.header!!, it.creationDate!!, it.preview) }
        model["preview_articles"] = articles
        return "home"
    }

    // Redirect admin to newly created article
    @PostMapping("/admin/create-article")
    fun createArticle(
        @ModelAttribute("article-header") articleHeader: String,

        @CorrectHtml
        @ModelAttribute("article-body") articleBody: String,
        model: Model
    ): String {
        val created = articleService.createArticle(articleHeader, articleBody)
        return "redirect:/cringe/${created.id}"
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintsViolationHandler(cex: ConstraintViolationException, model: Model): String {
        model["violations"] = cex.constraintViolations.map { it.message }
        return "errors"
    }

    @GetMapping("/cringe/{id}")
    fun getArticle(
        @PathVariable("id") articleId: Long,
        model: Model
    ): String {
        // TODO: implements join fetch for this query
        val article = articleService.getArticle(articleId)
        if (article == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)

        model["article"] = ArticleDto(article.id!!, article.header!!, article.creationDate!!, body = article.body)

        val comments = commentsService.getArticleComments(articleId)

        val CommentComp = Comparator
            .comparing<Comment, LocalDateTime> { it.creationDate }

        val graph = comments
            .groupingBy { it.responseTo }
            .aggregate { key, accumulator: PriorityQueue<Comment>?, element, first ->
                if (first) {
                    val acc = PriorityQueue(CommentComp)
                    acc.add(element)
                    acc
                } else {
                    accumulator!!.add(element)
                    accumulator
                }
            }

        val commentsToRender = ArrayList<CommentDto>()

        // WARNING!!!
        // This should be a directed graph
        // without ability to visit one node twice during dfs.
        // Otherwise, may result in incorrect view or infinite loop.

        fun dfs(v: Long?, depth: Int = 0) {
            val pq = graph[v]
            if (pq == null)
                return

            while (!pq.isEmpty()) {
                val com = pq.poll()
                commentsToRender.add(CommentDto(
                    id = com.id!!,
                    text = com.text,
                    author = com.author,
                    creationDateBacking = com.creationDate,
                    depth = depth
                ))

                dfs(com.id, depth + 1)
            }
        }

        val startTime = LocalDateTime.now()
        dfs(null)

        val duration = ChronoUnit.MICROS.between(startTime, LocalDateTime.now())
        log.info("Dfs took: $duration micros")

        model["comments"] = commentsToRender

        return "article"
    }
}
