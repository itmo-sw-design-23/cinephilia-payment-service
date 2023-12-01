package com.cinephilia.payment

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recommendations", consumes = ["application/json"])
class IndexController {
    @GetMapping
    @Operation(summary = "Main page")
    fun index() = "Hello world!"
}