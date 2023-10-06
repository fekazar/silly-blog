package edu.silly.blog

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage

@Configuration
class Config {
    @Bean
    fun templateEmailMessage(
        @Value("\${admin-email}") adminEmail: String,
        @Value("\${sender-email}") senderEmail: String,
    ): SimpleMailMessage = SimpleMailMessage().apply {
        from = senderEmail
        setTo(adminEmail)
    }
}
