package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentCreatedEvent
import java.util.*

fun PaymentAggregate.cancelPaymentCommand(
    paymentId: UUID = UUID.randomUUID(),
    user: User,
    movie: Movie,
): PaymentCanceledEvent {
    // TODO: business logic

    return PaymentCanceledEvent(
        paymentId = paymentId,
        user = user,
        movie = movie
    )
}