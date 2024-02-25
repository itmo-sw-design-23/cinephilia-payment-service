package com.cinephilia.payment.integration_events

import java.util.UUID

data class MoviePurchasedEvent(val movieId: UUID, val userId: UUID, val timestamp: Long)