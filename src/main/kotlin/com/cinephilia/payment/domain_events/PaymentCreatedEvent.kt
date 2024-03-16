package com.cinephilia.payment.domain_events

import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.model.User
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import com.cinephilia.payment.model.Movie
import java.util.*

@DomainEvent(name = PAYMENT_CREATED_EVENT_TAG)
class PaymentCreatedEvent(
        val paymentId: UUID,
        val user: User,
        val movie: Movie,
        createdAt: Long = System.currentTimeMillis()
) : Event<PaymentAggregate>(
    name = PAYMENT_CREATED_EVENT_TAG,
    createdAt = createdAt
)