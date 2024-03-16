package com.cinephilia.payment.projections

import com.cinephilia.payment.domain_events.PaymentCanceledEvent
import com.cinephilia.payment.domain_events.PaymentCreatedEvent
import com.cinephilia.payment.domain_events.PaymentSuccededEvent
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.integration_events.MoviePurchasedEvent
import com.cinephilia.payment.kafka.KafkaProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = PaymentAggregate::class, subscriberName = "payment-stream-subscriber"
)
class PaymentSubscriber(
    val kafkaProducer: KafkaProducer
) {
    val logger: Logger = LoggerFactory.getLogger(PaymentSubscriber::class.java)

    @SubscribeEvent
    fun paymentCreatedSubscriber(event: PaymentCreatedEvent) {
        logger.info("Payment created: {}", event.paymentId)
    }

    @SubscribeEvent
    fun paymentSucceededSubscriber(event: PaymentSuccededEvent) {
        logger.info("Payment succeded: {}", event.paymentId)
        val moviePurchasedEvent = MoviePurchasedEvent(event.movieId, event.userId, event.createdAt)
        kafkaProducer.syncSend("cinephilia.payments.movie-purchased", moviePurchasedEvent)
    }

    @SubscribeEvent
    fun paymentCanceledSubscriber(event: PaymentCanceledEvent) {
        logger.info("Payment canceled: {}", event.paymentId)
    }
}