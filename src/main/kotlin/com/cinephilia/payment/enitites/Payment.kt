package com.cinephilia.payment.enitites

import com.cinephilia.payment.enitites.enums.PaymentStatus
import com.cinephilia.payment.events.PaymentCreatedEvent
import ru.quipy.core.annotations.AggregateType
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.Aggregate
import ru.quipy.domain.AggregateState
import java.time.Instant
import java.util.*

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var closedAt: Long? = null
    private var status: PaymentStatus = PaymentStatus.New

    private lateinit var user: User
    private lateinit var movie: Movie

    override fun getId() = paymentId

    @StateTransitionFunc
    fun applyPaymentCreated(event: PaymentCreatedEvent) {
        paymentId = event.paymentId
        createdAt = event.createdAt
        user = event.user
        movie = event.movie
    }
}

