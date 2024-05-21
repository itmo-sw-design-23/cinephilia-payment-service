package com.cinephilia.payment.tracing

import com.mongodb.MongoClientSettings
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.mongo.v3_1.MongoTelemetry
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoTracing(private val openTelemetry: OpenTelemetry) {
    @Bean
    fun mongoClientSettingsBuilderCustomizer(): MongoClientSettingsBuilderCustomizer {
        val mongoTelemetry: MongoTelemetry = MongoTelemetry.builder(openTelemetry).build()
        return MongoClientSettingsBuilderCustomizer { builder: MongoClientSettings.Builder ->
            builder.addCommandListener(
                mongoTelemetry.newCommandListener()
            )
        }
    }
}