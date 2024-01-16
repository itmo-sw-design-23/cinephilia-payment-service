package com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.proceedPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.dtos.CreatePaymentRequestDto
import com.cinephilia.payment.dtos.CreatePaymentResponseDto
import com.cinephilia.payment.dtos.RejectPaymentDto
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.stripe.model.Event
import com.stripe.model.PaymentIntent
import com.stripe.model.StripeObject
import com.stripe.net.Webhook
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*


@RestController
@RequestMapping("/payments")
class PaymentController(
        val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {

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
//        println(event.type)
//        println(event.id)
        var stripeObject: StripeObject? = null
        stripeObject = event.dataObjectDeserializer.deserializeUnsafe();
        when(event.type){
            "payment_intent.succeeded" -> {
                val paymentIntent = stripeObject as PaymentIntent
                val paymentID = UUID.fromString(paymentIntent.metadata["paymentID"])
//                print("paymentID: ")
//                println(paymentID)
                PaymentEsService.update(paymentID) {
                    it.proceedPaymentCommand(paymentId = paymentID,
                        description = paymentIntent.metadata["film"].toString())
                }
            }
            "payment_intent.payment_failed", "payment_intent.canceled" -> {
                val paymentIntent = stripeObject as PaymentIntent
                val paymentID = UUID.fromString(paymentIntent.metadata["paymentID"])
                PaymentEsService.update(paymentID) {
                    it.cancelPaymentCommand(paymentId = paymentID)
                }
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