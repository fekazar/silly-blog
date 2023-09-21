package edu.silly.blog.repository

import edu.silly.blog.model.Article
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface ArticleRepository : Repository<Article, Long> {

    fun save(article: Article): Article

    fun findById(id: Long): Article?

    fun getAllByOrderByCreationDateDesc(): List<Article>

    @Query("SELECT new edu.silly.blog.model.Article(a.header, a.preview, a.creationDate, a.id) " +
            "FROM Article a " +
            "ORDER BY a.creationDate DESC")
    fun getPreviewOnly(): List<Article>
}
