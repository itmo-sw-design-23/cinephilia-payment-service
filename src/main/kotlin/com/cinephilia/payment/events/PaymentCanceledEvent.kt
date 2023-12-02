package com.cinephilia.payment.events

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.User
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

@DomainEvent(name = PAYMENT_CANCELED_EVENT_TAG)
class PaymentCanceledEvent(
    val paymentId: UUID,
    val user: User,
    val movie: Movie,
    createdAt: Long = System.currentTimeMillis()
) : Event<PaymentAggregate>(
    name = PAYMENT_CANCELED_EVENT_TAG,
    createdAt = createdAt
)