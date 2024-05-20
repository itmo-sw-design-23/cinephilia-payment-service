package com.cinephilia.payment.services

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

data class UserEntity(
    val deleted: Boolean,
    val id: String,
    val userName: String,
    val birthDate: Long,
    val email: String,
    val password: String,
    val gender: String,
    val country: String,
    val roles: List<String>
);

@Component
class JwtService {
    fun isJwtValid(authHeader: String): Boolean {
        val jwtParts = authHeader.split(' ')
        if (jwtParts[0] != "Bearer" || jwtParts.size <= 1) {
            return true
        }
        val response = WebClient.create("https://itmo-sd.letsdeploy.space/api/v1").get()
            .uri("/users/user-info")
            .header("Authorization", authHeader)
            .retrieve()
            .toEntity(UserEntity::class.java)
            .block()
            ?: return false
        return true
    }

    fun extractUser(authHeader: String): UserEntity? {

        val webClient = WebClient.create("https://itmo-sd.letsdeploy.space/api/v1")

        val response = webClient.get()
            .uri("/users/user-info")
            .header("Authorization", authHeader)
            .retrieve()
            .toEntity(UserEntity::class.java)
            .block()
            ?: return null

        return response.body
    }
}