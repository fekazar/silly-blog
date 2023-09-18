package edu.silly.blog.controller

import edu.silly.blog.model.CreateTokenDto
import edu.silly.blog.service.TokenService
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@Controller
class AdminController(
    val tokenService: TokenService
) {
    @GetMapping("/admin")
    fun admin() = "admin/admin"

    @GetMapping("admin/create-article")
    fun createArticle(): String {
        return "admin/create-article"
    }

    @GetMapping("admin/tokens")
    fun tokens(model: Model): String {
        val tokens = tokenService.getAllTokens() // TODO: make dto (entity will do)
        model["tokens"] = tokens
        return "/admin/tokens"
    }

    @PostMapping("/admin/create-token")
    fun createToken(
        @Valid // TODO: implement good validation
        createToken: CreateTokenDto,
    ): String {
        tokenService.createToken(createToken.token, createToken.role, createToken.description)
        return "redirect:/admin/tokens"
    }

    @DeleteMapping("/admin/delete-token")
    fun deleteToken(
        @RequestParam("token") token: String
    ) {
        // validation is not required
        throw ResponseStatusException(HttpStatus.NOT_IMPLEMENTED)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleViolations(e: ConstraintViolationException) {
        println("Violations: ${e.constraintViolations}")
    }
}
