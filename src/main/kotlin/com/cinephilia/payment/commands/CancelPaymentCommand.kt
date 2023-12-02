package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.enitites.User
import com.cinephilia.payment.events.PaymentCanceledEvent

import java.util.*

fun PaymentAggregateState.cancelPaymentCommand(
    paymentId: UUID,
): PaymentCanceledEvent {
    // TODO: business logic

    return PaymentCanceledEvent(paymentId = paymentId)
}