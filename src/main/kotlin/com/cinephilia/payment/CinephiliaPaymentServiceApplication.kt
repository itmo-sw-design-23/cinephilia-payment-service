package com.cinephilia.payment

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
@OpenAPIDefinition(
	info = Info(
		title = "Cinephilia's Payments API",
		version = "1.0.0",
		description = "Payments API for Cinephilia's streaming service"
	)
)
class CinephiliaPaymentServiceApplication

fun main(args: Array<String>) {
	runApplication<CinephiliaPaymentServiceApplication>(*args)
}
