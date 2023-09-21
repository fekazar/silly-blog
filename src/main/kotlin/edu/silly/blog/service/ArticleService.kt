package edu.silly.blog.service

import edu.silly.blog.model.Article
import edu.silly.blog.repository.ArticleRepository
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.springframework.stereotype.Component
import java.io.StringWriter
import java.time.LocalDateTime

private const val MAX_PREVIEW_WORDS = 50

@Component
class ArticleService(
    val articleRepository: ArticleRepository
) {
    fun createArticle(header: String, body: String): Article {
        makePreview(body)
        val article = Article(header, body, makePreview(body), LocalDateTime.now())
        return articleRepository.save(article)
    }

    fun getArticle(id: Long) = articleRepository.findById(id)

    fun getLatestArticles(): List<Article> = articleRepository.getPreviewOnly()

    private fun makePreview(body: String): String {
        val reader = SAXReader()
        val doc = reader.read(body.byteInputStream())

        val root = doc.rootElement
        var wc = 0

        fun dfs(cur: Element, previewPar: Element) {
            if (wc == MAX_PREVIEW_WORDS)
                return

            if (cur.elements().isEmpty()) {
                val words = cur.text.split(" ", "\n")
                    .map { it.trim() }
                    .filterNot { it.isEmpty() }
                    .take(MAX_PREVIEW_WORDS - wc)

                println("words: $words")
                wc += words.size

                val toAppend = DocumentHelper.createElement(cur.name)
                // TODO: list more separators
                toAppend.text = words.joinToString(separator = " ", prefix = " ", postfix = " ")
                previewPar.add(toAppend)

                return
            }

            val curPreview = DocumentHelper.createElement("div")
            for (child in cur.elements())
                dfs(child, curPreview)

            previewPar.add(curPreview)
        }

        val preview = DocumentHelper.createElement("div")
        dfs(root, preview)

        val dest = StringWriter()
        preview.write(dest)
        return dest.toString()
    }
}