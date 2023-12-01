package com.cinephilia.payment

import com.cinephilia.payment.enitites.PaymentAggregate
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recommendations", consumes = ["application/json"])
class PaymentController {
    @GetMapping
    @Operation(summary = "Get recommendations by user ID")
    fun index() = "Hello world!"

    @PostMapping
    fun createPayment() {
        val payment = PaymentAggregate();
    }
}