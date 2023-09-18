package edu.silly.blog.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationFilter(requestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requestMatcher) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        val token: String? = request.getParameter("token")
        if (token == null)
            return null

        return authenticationManager.authenticate(TokenAuthentication.unauthenticated(token))
    }
}