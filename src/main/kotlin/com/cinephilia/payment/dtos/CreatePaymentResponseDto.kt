package com.cinephilia.payment.dtos

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.User
import java.util.UUID

data class CreatePaymentRequestDto(val user: User, val movie: Movie)

data class CreatePaymentResponseDto(val id: UUID)