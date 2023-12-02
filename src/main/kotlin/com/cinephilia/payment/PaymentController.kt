package com.cinephilia.payment

import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.enitites.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/recommendations", consumes = ["application/json"])
class PaymentController {
    @GetMapping
    @Operation(summary = "Get recommendations by user ID")
    fun index() = "Hello world!"

    @PostMapping
    fun createPayment() {
        val payment = PaymentAggregate()
        val createdEvent = payment.createPaymentCommand(user = User(UUID.randomUUID()), movie = Movie(UUID.randomUUID(), 5000.0))

        val paymentState = PaymentAggregateState()
        paymentState.applyPaymentCreated(createdEvent)
    }
}