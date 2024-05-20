package com.cinephilia.payment.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component


@Component
class PaymentsMetrics(private val meterRegistry: MeterRegistry) {
    val successCounter = Counter.builder("stripe_requests").tag("result", "success").register(meterRegistry)
    val failCounter = Counter.builder("stripe_requests").tag("result", "fail").register(meterRegistry)

    fun incrementSuccess() {
        successCounter.increment()
    }

    fun incrementFail() {
        failCounter.increment()
    }
}