package com.cinephilia.payment.enitites

import com.cinephilia.payment.enitites.enums.PaymentStatus
import com.cinephilia.payment.events.PaymentCanceledEvent
import com.cinephilia.payment.events.PaymentCreatedEvent
import com.cinephilia.payment.events.PaymentFailedEvent
import com.cinephilia.payment.events.PaymentSuccededEvent
import ru.quipy.core.annotations.AggregateType
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.Aggregate
import ru.quipy.domain.AggregateState
import java.util.*

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private lateinit var externalId: UUID
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

    @StateTransitionFunc
    fun applyPaymentFailed(event: PaymentFailedEvent) {
        closedAt = event.createdAt
        status = PaymentStatus.Canceled
    }

    @StateTransitionFunc
    fun applyPaymentSucceded(event: PaymentSuccededEvent) {
        closedAt = event.createdAt
        externalId = event.externalId
        status = PaymentStatus.Succeded
    }

    @StateTransitionFunc
    fun applyPaymentCanceled(event: PaymentCanceledEvent) {
        closedAt = event.createdAt
        status = PaymentStatus.Canceled
    }
}

