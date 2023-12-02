package com.cinephilia.payment.controllers

import com.cinephilia.payment.commands.cancelPaymentCommand
import com.cinephilia.payment.commands.createPaymentCommand
import com.cinephilia.payment.commands.proceedPaymentCommand
import com.cinephilia.payment.commands.rejectPaymentCommand
import com.cinephilia.payment.dtos.CreatePaymentResponseDto
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import com.cinephilia.payment.dtos.CreatePaymentRequestDto
import com.cinephilia.payment.dtos.ProceedPaymentDto
import com.cinephilia.payment.dtos.RejectPaymentDto
import com.cinephilia.payment.enitites.PaymentAggregate
import com.cinephilia.payment.enitites.PaymentAggregateState
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentController(
        val PaymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {

    @PutMapping
    fun createPayment(@RequestBody dto: CreatePaymentRequestDto): CreatePaymentResponseDto {
        val payment = PaymentEsService.create {it.createPaymentCommand(user = dto.user, movie = dto.movie) }

        return CreatePaymentResponseDto(payment.id);
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