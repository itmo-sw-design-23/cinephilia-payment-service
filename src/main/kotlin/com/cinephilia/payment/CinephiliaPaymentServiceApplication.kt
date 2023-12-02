package com.cinephilia.payment

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

//@SpringBootApplication(scanBasePackages = ["ru.quipy"])
//@OpenAPIDefinition(
//        info = Info(
//                title = "Cinephilia's Payment API",
//                version = "0.0.1",
//                description = "Payment API for Cinephilia's streaming service"
//        )
//)
@SpringBootApplication(scanBasePackages = ["ru.quipy", "com.cinephilia.payment"])
class CinephiliaPaymentServiceApplication

fun main(args: Array<String>) {
    runApplication<CinephiliaPaymentServiceApplication>(*args)
}
