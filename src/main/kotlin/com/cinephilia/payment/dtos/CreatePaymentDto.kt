package com.cinephilia.payment.dtos

import com.cinephilia.payment.enitites.Movie
import com.cinephilia.payment.enitites.User

data class CreatePaymentDto(val user: User, val movie: Movie);