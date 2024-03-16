package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.domain_events.PaymentSuccededEvent
import java.util.*

fun PaymentAggregateState.proceedPaymentCommand(
    paymentId: UUID = UUID.randomUUID(),
    description: String,
    movieId: UUID,
    userId: UUID
): PaymentSuccededEvent {
    return PaymentSuccededEvent(
        paymentId = paymentId,
        description = description,
        movieId = movieId,
        userId = userId
    )
}