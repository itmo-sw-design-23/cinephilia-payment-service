package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentSuccededEvent
import java.util.*

fun PaymentAggregateState.proceedPaymentCommand(
    paymentId: UUID = UUID.randomUUID(),
    externalId: UUID,
    user: User,
    movie: Movie,
    description: String
): PaymentSuccededEvent {
    // TODO: business logic
    return PaymentSuccededEvent(
        paymentId = paymentId,
        user = user,
        movie = movie,
        description = description,
        externalId = externalId
    )
}