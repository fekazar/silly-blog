package edu.silly.blog.repository

import edu.silly.blog.model.Token
import org.springframework.data.repository.Repository

interface TokenRepository : Repository<Token, String> {
    fun save(token: Token): Token
    fun findAll(): List<Token>
    fun deleteById(id: String)
}