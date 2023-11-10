package com.cinephiliapaymentservice.domain

import com.cinephiliapaymentservice.domain.enums.PaymentStatus
import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import ru.quipy.domain.AggregateState
import java.time.Instant
import java.util.*

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private var paymentId: UUID = UUID.randomUUID()
    private val createdAt: Instant = Instant.now()
    private var closedAt: Instant? = null
    private var status: PaymentStatus = PaymentStatus.New

    private var user: User? = null
    private var movie: Movie? = null

    override fun getId() = paymentId
}

