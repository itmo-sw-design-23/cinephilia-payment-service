package com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.proceedPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.model.CreatePaymentRequestDto
import com.cinephilia.payment.model.CreatePaymentResponseDto
import com.cinephilia.payment.model.RejectPaymentDto
import com.stripe.model.Event
import com.stripe.model.PaymentIntent
import com.stripe.model.StripeObject
import com.stripe.net.Webhook
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
        val event = PaymentEsService.create {
            it.createPaymentCommand(user = createPaymentRequestDto.user, movie = createPaymentRequestDto.movie)
        }

        return ResponseEntity<CreatePaymentResponseDto>(
            CreatePaymentResponseDto(event.paymentId, event.paymentLink),
            HttpStatus.OK
        );
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
            currentState.movie
        )

        return ResponseEntity(dto, HttpStatus.OK)
    }

    override fun proceedPayment(body: String, stripeSignature: String?): ResponseEntity<Unit> {
        val endpointSecret = System.getenv("STRIPE_SIGNATURE_KEY")

        var event: Event? = null

        event = Webhook.constructEvent(
            body, stripeSignature, endpointSecret
        )
        var stripeObject: StripeObject? = null
        stripeObject = event.dataObjectDeserializer.deserializeUnsafe()

        when (event.type) {
            "payment_intent.succeeded" -> {
                val paymentIntent = stripeObject as PaymentIntent

                val paymentID = UUID.fromString(paymentIntent.metadata["paymentID"])
                val currentState = PaymentEsService.getState(paymentID)

                PaymentEsService.update(paymentID) {
                    it.proceedPaymentCommand(
                        paymentId = paymentID,
                        description = paymentIntent.metadata["film"].toString(),
                        movieId = currentState!!.movie.id!!,
                        userId = currentState.user.id!!
                    )
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

        return ResponseEntity<Unit>(HttpStatus.OK)
    }

    override fun rejectPayment(id: UUID, rejectPaymentDto: RejectPaymentDto): ResponseEntity<Unit> {
        PaymentEsService.update(id) {
            it.rejectPaymentCommand(id, rejectPaymentDto.description)
        }
        return ResponseEntity<Unit>(HttpStatus.OK)

    }
}