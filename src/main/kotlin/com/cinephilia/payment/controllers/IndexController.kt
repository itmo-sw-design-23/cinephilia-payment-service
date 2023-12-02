package com.cinephilia.payment.controllers

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class IndexController {
    @GetMapping
    fun index() = "Hello world!"
}