package edu.silly.blog.security

import edu.silly.blog.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.NullRememberMeServices
import org.springframework.security.web.context.HttpSessionSecurityContextRepository

@Configuration
class SecurityConfig {
    @Bean
    fun securityChain(
        http: HttpSecurity,
        tokenService: TokenService,
        rememberTokenServices: RememberTokenServices,
    ): SecurityFilterChain {
        http {
            csrf {
                // Disabled for dev
                // TODO: setup csrf
                disable()
            }

            authorizeRequests {
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

            // TODO: implement remember me services
            rememberMe {
                //rememberMeServices = NullRememberMeServices()
                rememberMeServices = rememberTokenServices
            }
        }

        http.apply(TokenAuthenticationConfigurer(tokenService))

        return http.build()
    }
}