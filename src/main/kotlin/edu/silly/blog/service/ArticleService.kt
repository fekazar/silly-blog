package edu.silly.blog.service

import edu.silly.blog.model.Article
import edu.silly.blog.repository.ArticleRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ArticleService(
    val articleRepository: ArticleRepository
) {
    fun createArticle(header: String, body: String): Article {
        val article = Article(header, body, LocalDateTime.now())
        return articleRepository.save(article)
    }

    fun getArticle(id: Long) = articleRepository.findById(id)

    fun getLatestArticles(): List<Article> = articleRepository.getAllByOrderByCreationDateDesc()
}