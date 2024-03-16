package com.cinephilia.payment.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.domain_events.PaymentCreatedEvent
import com.cinephilia.payment.domain_events.PaymentSuccededEvent
import com.cinephilia.payment.enitites.PaymentAggregateState
import com.cinephilia.payment.integration_events.MoviePurchasedEvent
import com.cinephilia.payment.kafka.KafkaProducer
import ru.quipy.core.EventSourcingService
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(
    aggregateClass = PaymentAggregate::class, subscriberName = "payment-stream-subscriber"
)
class PaymentSubscriber(
    val kafkaProducer: KafkaProducer,
    val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {
    val logger: Logger = LoggerFactory.getLogger(PaymentSubscriber::class.java)

    @SubscribeEvent
    fun paymentCreatedSubscriber(event: PaymentCreatedEvent) {
        logger.info("Payment created: {}", event.paymentId)
    }

    @SubscribeEvent
    fun paymentSucceededSubscriber(event: PaymentSuccededEvent) {
        logger.info("Payment succeded: {}", event.paymentId)
        val payment = paymentEsService.getState(event.paymentId) ?: return

        val moviePurchasedEvent = MoviePurchasedEvent(payment.movie.id!!, payment.user.id!!, event.createdAt)
        kafkaProducer.syncSend("cinephilia.payments.movie-purchased", moviePurchasedEvent)
    }

    @SubscribeEvent
    fun paymentCanceledSubscriber(event: PaymentCanceledEvent) {
        logger.info("Payment canceled: {}", event.paymentId)
    }
}