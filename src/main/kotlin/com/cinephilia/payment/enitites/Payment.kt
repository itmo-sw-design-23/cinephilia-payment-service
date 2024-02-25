package com.cinephilia.payment.enitites

import com.cinephilia.payment.model.User
import com.cinephilia.payment.model.Movie
import com.cinephilia.payment.model.PaymentStatus
import com.cinephilia.payment.domain_events.PaymentCanceledEvent
import com.cinephilia.payment.domain_events.PaymentCreatedEvent
import com.cinephilia.payment.domain_events.PaymentFailedEvent
import com.cinephilia.payment.domain_events.PaymentSuccededEvent
import ru.quipy.core.annotations.AggregateType
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.Aggregate
import ru.quipy.domain.AggregateState
import java.util.*

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    lateinit var paymentId: UUID
    private lateinit var externalId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var closedAt: Long? = null
    var status: PaymentStatus = PaymentStatus.new

    lateinit var user: User
    lateinit var movie: Movie

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
        status = PaymentStatus.canceled
    }

    @StateTransitionFunc
    fun applyPaymentSucceded(event: PaymentSuccededEvent) {
        closedAt = event.createdAt
        status = PaymentStatus.succeded
    }

    @StateTransitionFunc
    fun applyPaymentCanceled(event: PaymentCanceledEvent) {
        closedAt = event.createdAt
        status = PaymentStatus.canceled
    }
}

