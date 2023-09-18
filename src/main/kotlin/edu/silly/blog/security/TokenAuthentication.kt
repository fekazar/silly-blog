package edu.silly.blog.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class TokenAuthentication(
    val token: String,
    authorities: List<GrantedAuthority>?
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials(): Any? = null
    override fun getPrincipal(): Any? = null

    companion object {
        @JvmStatic
        fun authenticated(token: String, authorities: List<GrantedAuthority>): TokenAuthentication {
            val auth = TokenAuthentication(token, authorities)
            auth.isAuthenticated = true
            return auth
        }

        @JvmStatic
        fun unauthenticated(token: String) = TokenAuthentication(token, null)
    }
}