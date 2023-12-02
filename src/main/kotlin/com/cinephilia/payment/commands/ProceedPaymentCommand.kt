package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentSuccededEvent
import java.util.*

fun PaymentAggregateState.proceedPaymentCommand(
    paymentId: UUID = UUID.randomUUID(),
    description: String
): PaymentSuccededEvent {
    return PaymentSuccededEvent(
        paymentId = paymentId,
        description = description
    )
}