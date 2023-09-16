package edu.silly.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PingController {
    @GetMapping("/ping")
    fun ping() = "pong"
}