package com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.dtos.CreatePaymentRequestDto
import com.cinephilia.payment.dtos.CreatePaymentResponseDto
import com.cinephilia.payment.dtos.RejectPaymentDto
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.stripe.exception.SignatureVerificationException
import com.stripe.model.Event
import com.stripe.net.Webhook
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/payments")
class xPaymentController(
        val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
): PaymentApi {

    @PutMapping
    fun createPayment(@RequestBody dto: CreatePaymentRequestDto): CreatePaymentResponseDto {
        val payment = PaymentEsService.create {it.createPaymentCommand(user = dto.user, movie = dto.movie) }

        return CreatePaymentResponseDto(payment.id);
    }

    @GetMapping("/{id}")
    fun getPayment(@PathVariable id: UUID): PaymentAggregateState? {
        return PaymentEsService.getState(id)
    }

    @PutMapping("/{id}/cancel")
    fun cancelPayment(@PathVariable id: UUID) {
        PaymentEsService.update(id) {
            it.cancelPaymentCommand(id)
        }
    }

    var created : Boolean = false
    @PostMapping("/proceed")
    fun proceedPayment(@RequestBody payload: String, @RequestHeader("Stripe-Signature") sigHeader: String) {
        val endpointSecret = "whsec_95e2a691360d96b09df1403804b329991c6dff44872dca42b15bf05175fe7129"

        var event: Event? = null

        // Verify webhook signature and extract the event.
        // See https://stripe.com/docs/webhooks#verify-events for more information.

        // Verify webhook signature and extract the event.
        // See https://stripe.com/docs/webhooks#verify-events for more information.
        event = Webhook.constructEvent(
            payload, sigHeader, endpointSecret
        )
        println(event.type)
        if (event.type.equals("payment_intent.created")){
            created = true
        } else {
            if (!created) {
                throw Exception()
            }
        }
    }

    @PutMapping("/{id}/reject")
    fun rejectPayment(@PathVariable id: UUID, @RequestBody dto: RejectPaymentDto) {
        PaymentEsService.update(id) {
            it.rejectPaymentCommand(id, dto.description)
        }
    }
}