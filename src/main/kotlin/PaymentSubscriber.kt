package ru.quipy.projections

import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.events.PaymentCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
        aggregateClass = PaymentAggregate::class, subscriberName = "demo-subs-stream"
)
class PaymentSubscriber {
    val logger: Logger = LoggerFactory.getLogger(PaymentSubscriber::class.java)

    @SubscribeEvent
    fun paymentCreatedSubscriber(event: PaymentCreatedEvent) {
        logger.info("Task created: {}", event.paymentId)
    }
}