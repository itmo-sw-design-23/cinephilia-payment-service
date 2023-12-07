package com.cinephilia.payment.events

import com.cinephilia.payment.enitites.PaymentAggregate
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import com.cinephilia.payment.events.PAYMENT_FAILED_EVENT_TAG
import java.util.*

@DomainEvent(name = PAYMENT_FAILED_EVENT_TAG)
class PaymentFailedEvent(
    val paymentId: UUID,
    val description: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<PaymentAggregate>(
    name = PAYMENT_FAILED_EVENT_TAG,
    createdAt = createdAt
)