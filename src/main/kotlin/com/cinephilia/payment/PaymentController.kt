package com.cinephilia.payment

import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.dtos.CreatePaymentDto
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentController(
        val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {

    @PostMapping
    fun createPayment(dto: CreatePaymentDto) {
        PaymentEsService.create { it.createPaymentCommand(user = dto.user, movie = dto.movie) }
    }
}