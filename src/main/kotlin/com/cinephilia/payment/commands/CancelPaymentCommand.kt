package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.domain_events.PaymentCanceledEvent

import java.util.*

fun PaymentAggregateState.cancelPaymentCommand(
    paymentId: UUID,
): PaymentCanceledEvent {
    // TODO: business logic

    return PaymentCanceledEvent(paymentId = paymentId)
}