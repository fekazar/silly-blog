package edu.silly.blog.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

// Cringe class to remove dependency from the 'username' term.
// Also, to remove the dev-only generated password with
// UserDetailsServiceAutoConfiguration

@Component
class NoOpUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String?) =
        throw IllegalStateException("Noop user details service cannot find users")
}