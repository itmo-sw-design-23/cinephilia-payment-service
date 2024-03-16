package com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.model.CreatePaymentRequestDto
import com.cinephilia.payment.model.CreatePaymentResponseDto
import com.cinephilia.payment.model.RejectPaymentDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*
import com.cinephilia.payment.model.PaymentAggregateState as PaymentAggregateStateDto

@RestController
class PaymentController(
    val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) : PaymentApi {

    override fun cancelPayment(id: UUID): ResponseEntity<Unit> {
        PaymentEsService.update(id) {
            it.cancelPaymentCommand(id)
        }
        return ResponseEntity<Unit>(HttpStatus.OK)
    }

    override fun createPayment(createPaymentRequestDto: CreatePaymentRequestDto): ResponseEntity<CreatePaymentResponseDto> {
        val payment = PaymentEsService.create {it.createPaymentCommand(user = createPaymentRequestDto.user, movie = createPaymentRequestDto.movie) }
        return ResponseEntity<CreatePaymentResponseDto>(CreatePaymentResponseDto(payment.id), HttpStatus.OK);
    }

    override fun getPaymentById(id: UUID): ResponseEntity<PaymentAggregateStateDto> {
        val currentState = PaymentEsService.getState(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val dto = PaymentAggregateStateDto(
            currentState.paymentId,
            currentState.createdAt,
            currentState.status,
            externalId = null,
            currentState.closedAt,
            currentState.user,
            currentState.movie)

        return ResponseEntity(dto, HttpStatus.OK)
    }

    @PostMapping("/proceed")
    fun proceedPayment(@RequestBody payload: String, @RequestHeader("Stripe-Signature") sigHeader: String) {
        val endpointSecret = "whsec_95e2a691360d96b09df1403804b329991c6dff44872dca42b15bf05175fe7129"

        var event: Event? = null

        event = Webhook.constructEvent(
            payload, sigHeader, endpointSecret
        )
        var stripeObject: StripeObject? = null
        stripeObject = event.dataObjectDeserializer.deserializeUnsafe();
        when(event.type){
            "payment_intent.succeeded" -> {
                val paymentIntent = stripeObject as PaymentIntent
                val paymentID = UUID.fromString(paymentIntent.metadata["paymentID"])
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

    override fun rejectPayment(id: UUID, rejectPaymentDto: RejectPaymentDto): ResponseEntity<Unit> {
        PaymentEsService.update(id) {
            it.rejectPaymentCommand(id, rejectPaymentDto.description)
        }
        return ResponseEntity<Unit>(HttpStatus.OK)

    }
}