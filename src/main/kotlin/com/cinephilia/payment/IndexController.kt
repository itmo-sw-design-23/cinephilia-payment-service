package com.cinephilia.payment

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class IndexController {
    @GetMapping
    fun index() = "Hello world!"
}