package ru.quipy.projections.com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.proceedPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.dtos.CreatePaymentDto
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.projections.com.cinephilia.payment.dtos.ProceedPaymentDto
import ru.quipy.projections.com.cinephilia.payment.dtos.RejectPaymentDto
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentController(
        val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {

    @PutMapping
    fun createPayment(dto: CreatePaymentDto) {
        PaymentEsService.create { it.createPaymentCommand(user = dto.user, movie = dto.movie) }
    }

    @GetMapping("/{id}")
    fun getPayment(@PathVariable id: UUID): PaymentAggregateState? {
        return PaymentEsService.getState(id)
    }

    @PutMapping("/{id}/cancel")
    fun cancelPayment(@PathVariable id: UUID) {
        PaymentEsService.update(id) {
            it.cancelPaymentCommand(id)
        }
    }

    @PutMapping("/{id}/proceed")
    fun proceedPayment(@PathVariable id: UUID, @RequestBody dto: ProceedPaymentDto) {
        PaymentEsService.update(id) {
            it.proceedPaymentCommand(id, dto.description)
        }
    }

    @PutMapping("/{id}/reject")
    fun rejectPayment(@PathVariable id: UUID, @RequestBody dto: RejectPaymentDto) {
        PaymentEsService.update(id) {
            it.rejectPaymentCommand(id, dto.description)
        }
    }
}