package edu.silly.blog.service

import edu.silly.blog.model.Token
import edu.silly.blog.repository.TokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TokenService(
    val tokenRepository: TokenRepository
) {
    fun createToken(token: String, role: String, description: String) {
        val tokenToCreate = Token(token, role, description, LocalDateTime.now())
        tokenRepository.save(tokenToCreate)
    }
}
