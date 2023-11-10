package com.cinephiliapaymentservice.domain

import com.cinephiliapaymentservice.domain.enums.PaymentStatus
import java.time.Instant
import java.util.*

data class Payment(
    val user: User,
    val movie: Movie,
) {
    var closedAt: Instant? = null
    var status: PaymentStatus = PaymentStatus.New
    val createdAt: Instant = Instant.now()
    val id: UUID = UUID.randomUUID()

    fun finish() {
        check(status == PaymentStatus.New) {"Only payments at New status can be finished"}

        status = PaymentStatus.Finished
        closedAt = Instant.now()
    }

    fun cancel() {
        check(status == PaymentStatus.New) {"Only payments at New status can be canceled"}

        status = PaymentStatus.Canceled
        closedAt = Instant.now()
    }
}
