package com.cinephilia.payment.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.events.PaymentCanceledEvent
import com.cinephilia.payment.events.PaymentCreatedEvent
import com.cinephilia.payment.events.PaymentSuccededEvent
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
    @SubscribeEvent
    fun paymentSuccededSubscriber(event: PaymentSuccededEvent) {
        logger.info("Payment succeded: {}", event.paymentId)
    }
    @SubscribeEvent
    fun paymentCanceledSubscriber(event: PaymentCanceledEvent) {
        logger.info("Payment canceled: {}", event.paymentId)
    }
}