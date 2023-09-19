package edu.silly.blog.security

import edu.silly.blog.service.TokenService
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationConfigurer(
    val tokenService: TokenService
) : AbstractHttpConfigurer<TokenAuthenticationConfigurer, HttpSecurity>() {
    private var requestMatcher: RequestMatcher = AntPathRequestMatcher("/login", HttpMethod.POST.name())

    override fun init(builder: HttpSecurity) {
        super.init(builder)
    }

    override fun configure(http: HttpSecurity) {
        // Maybe this provider should be set NOT in configurer?
        // Assume this may cause undefined behaviour if client set his own provider (shall he?)
        http.authenticationProvider(TokenAuthenticationProvider(tokenService))

        val filter = TokenAuthenticationFilter(requestMatcher)
        val authManager = http.getSharedObject(AuthenticationManager::class.java)
        val rememberMeServices = http.getSharedObject(RememberMeServices::class.java)
        val contextRepository = http.getSharedObject(SecurityContextRepository::class.java)

        filter.rememberMeServices = rememberMeServices
        filter.setAuthenticationManager(authManager)
        filter.setRequiresAuthenticationRequestMatcher(requestMatcher)
        filter.setSecurityContextRepository(contextRepository)
        filter.setAuthenticationFailureHandler(SimpleUrlAuthenticationFailureHandler("/login?error"))

        http.addFilterBefore(filter, BasicAuthenticationFilter::class.java)
    }

    fun requestMatcher(requestMatcher: RequestMatcher): TokenAuthenticationConfigurer {
        this.requestMatcher = requestMatcher;
        return this
    }
}