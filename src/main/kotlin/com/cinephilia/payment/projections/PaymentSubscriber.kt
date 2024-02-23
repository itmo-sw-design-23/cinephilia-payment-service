package com.cinephilia.payment.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.events.PaymentCanceledEvent
import com.cinephilia.payment.events.PaymentCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
        aggregateClass = PaymentAggregate::class, subscriberName = "payment-stream-subscriber"
)
class PaymentSubscriber {
    val logger: Logger = LoggerFactory.getLogger(PaymentSubscriber::class.java)

    @SubscribeEvent
    fun paymentCreatedSubscriber(event: PaymentCreatedEvent) {
        logger.info("Payment created: {}", event.paymentId)
    }
}