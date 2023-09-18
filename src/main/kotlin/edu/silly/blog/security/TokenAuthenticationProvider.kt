package edu.silly.blog.security

import edu.silly.blog.service.TokenService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

class TokenAuthenticationProvider(
    private val tokenService: TokenService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is TokenAuthentication)
            return null

        val token = tokenService.findById(authentication.token)
        if (token == null)
            throw BadCredentialsException("Incorrect token")

        val authority = SimpleGrantedAuthority(token.role)
        return TokenAuthentication.authenticated(token.id, listOf(authority))
    }

    override fun supports(authentication: Class<*>?): Boolean =
        authentication?.isAssignableFrom(TokenAuthentication::class.java) ?: false
}