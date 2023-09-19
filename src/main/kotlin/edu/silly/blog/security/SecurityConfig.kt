package edu.silly.blog.security

import edu.silly.blog.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig {
    @Bean
    fun securityChain(
        http: HttpSecurity,
        tokenService: TokenService,
        rememberTokenServices: RememberTokenServices,
    ): SecurityFilterChain {
        http {
            logout {
                logoutRequestMatcher = AntPathRequestMatcher("/logout", HttpMethod.GET.name())
                logoutSuccessUrl = "/login"
                deleteCookies(REMEMBER_TOKEN_COOKIE)
            }

            authorizeRequests {
                authorize("/styles/**", permitAll)
                authorize("/login/**", permitAll)
                authorize("/admin/**", hasRole("ADMIN"))
                authorize(anyRequest, authenticated)
            }

            exceptionHandling {
                authenticationEntryPoint = LoginUrlAuthenticationEntryPoint("/login")
            }

            securityContext {
                securityContextRepository = HttpSessionSecurityContextRepository()
            }

            rememberMe {
                rememberMeServices = rememberTokenServices
            }
        }

        http.apply(TokenAuthenticationConfigurer(tokenService))

        return http.build()
    }
}