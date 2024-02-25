package com.cinephilia.payment.commands

import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.domain_events.PaymentFailedEvent
import java.util.*

fun PaymentAggregateState.rejectPaymentCommand(
    paymentId: UUID,
    description: String
): PaymentFailedEvent {

    // TODO: business logic

    return PaymentFailedEvent(
        paymentId = paymentId,
        description = description
    )
}