package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentCreatedEvent
import java.util.*

fun PaymentAggregateState.createPaymentCommand(
        paymentId: UUID = UUID.randomUUID(),
        user: User,
        movie: Movie,
): PaymentCreatedEvent {
    // TODO: business logic
    
    return PaymentCreatedEvent(
        paymentId = paymentId,
        user = user,
        movie = movie
    )
}