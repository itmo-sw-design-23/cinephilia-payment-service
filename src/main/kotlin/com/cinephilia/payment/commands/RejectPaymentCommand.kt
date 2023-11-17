package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentFailedEvent
import java.util.*

fun PaymentAggregate.rejectPaymentCommand(
    paymentId: UUID,
    user: User,
    movie: Movie,
    description: String
): PaymentFailedEvent {

    // TODO: business logic

    return PaymentFailedEvent(
        paymentId = paymentId,
        user = user,
        movie = movie,
        description = description
    )
}