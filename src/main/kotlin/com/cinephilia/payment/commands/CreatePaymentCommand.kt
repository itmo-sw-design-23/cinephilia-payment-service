package com.cinephilia.payment.commands

import CreatePayment
import com.cinephilia.payment.model.Movie
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.model.User
import com.cinephilia.payment.domain_events.PaymentCreatedEvent
import java.util.*

fun PaymentAggregateState.createPaymentCommand(
        paymentId: UUID = UUID.randomUUID(),
        user: User,
        movie: Movie,
): PaymentCreatedEvent {
    val paymentLink = CreatePayment(movie.name, paymentId)

    return PaymentCreatedEvent(
        paymentId = paymentId,
        user = user,
        movie = movie,
        paymentLink = paymentLink
    )
}