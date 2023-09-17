package edu.silly.blog.repository

import edu.silly.blog.model.Article
import org.springframework.data.repository.Repository

interface ArticleRepository : Repository<Article, Long> {
    fun save(article: Article): Article
    fun findById(id: Long): Article?
}