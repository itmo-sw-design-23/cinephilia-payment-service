package com.cinephilia.payment.enitites

import com.cinephilia.payment.enitites.enums.PaymentStatus
import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import ru.quipy.domain.AggregateState
import java.time.Instant
import java.util.*

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private val paymentId: UUID = UUID.randomUUID()
    private val createdAt: Instant = Instant.now()
    private lateinit var closedAt: Instant
    private var status: PaymentStatus = PaymentStatus.New

    private lateinit var user: User
    private lateinit var movie: Movie

    override fun getId() = paymentId
}

