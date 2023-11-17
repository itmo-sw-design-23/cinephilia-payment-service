package com.cinephilia.payment.events

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.User
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

@DomainEvent(name = PAYMENT_FAILED_EVENT_TAG)
class PaymentFailedEvent(
    val paymentId: UUID,
    val user: User,
    val movie: Movie,
    val description: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<PaymentAggregate>(
    name = PAYMENT_FAILED_EVENT_TAG,
    createdAt = createdAt
)