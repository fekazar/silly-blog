package edu.silly.blog.security

import edu.silly.blog.service.TokenService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.stereotype.Component

const val REMEMBER_TOKEN_COOKIE = "REMEMBERTOKEN"

@Component
class RememberTokenServices(
    val tokenService: TokenService
) : RememberMeServices {

    override fun autoLogin(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        if (request.cookies == null)
            return null

        val cookie = request.cookies.find { it.name == REMEMBER_TOKEN_COOKIE }
        if (cookie == null)
            return null

        val tokenId = cookie.value
        val token = tokenService.findById(tokenId)

        return token?.let { TokenAuthentication.authenticated(tokenId, listOf(SimpleGrantedAuthority(token.role))) }
    }

    override fun loginFail(request: HttpServletRequest, response: HttpServletResponse) {
        val cookies = request.cookies
        if (cookies == null)
            return

        val rememberTokenCookie = cookies
            .find { it.name == REMEMBER_TOKEN_COOKIE }
            ?.also { it.maxAge = 0 }

        if (rememberTokenCookie != null)
            response.addCookie(rememberTokenCookie)
    }

    override fun loginSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        successfulAuthentication: Authentication
    ) {
        // TODO: make better check
        if (request.getParameter("should-remember") != "on")
            return

        if (successfulAuthentication !is TokenAuthentication)
            return;

        val rememberCookie = Cookie(REMEMBER_TOKEN_COOKIE, successfulAuthentication.token)
        response.addCookie(rememberCookie)
    }
}