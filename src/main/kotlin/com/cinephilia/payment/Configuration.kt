package com.cinephilia.recommendation.domain.config

import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.projections.PaymentSubscriber
import ru.quipy.streams.AggregateEventStreamManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*

@Configuration
@RequiredArgsConstructor
class Configuration {
    private val logger = LoggerFactory.getLogger(Configuration::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var paymentEventSubscriber: PaymentSubscriber

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @Bean
    fun paymentEsService() =
            eventSourcingServiceFactory.create<UUID, PaymentAggregate, PaymentAggregateState>()

    @PostConstruct
    fun init() {
        subscriptionsManager.subscribe<PaymentAggregate>(paymentEventSubscriber)

        eventStreamManager.maintenance {
            onRecordHandledSuccessfully { streamName, eventName ->
                logger.info("Stream $streamName successfully processed record of $eventName")
            }

            onBatchRead { streamName, batchSize ->
                logger.info("Stream $streamName read batch size: $batchSize")
            }
        }
    }

}