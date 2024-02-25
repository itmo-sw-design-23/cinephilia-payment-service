package com.cinephilia.payment.domain_events

import com.cinephilia.payment.enitites.PaymentAggregate
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

@DomainEvent(name = PAYMENT_SUCCEDED_EVENT_TAG)
class PaymentSuccededEvent(
    val paymentId: UUID,
    val description: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<PaymentAggregate>(
    name = PAYMENT_SUCCEDED_EVENT_TAG,
    createdAt = createdAt
)