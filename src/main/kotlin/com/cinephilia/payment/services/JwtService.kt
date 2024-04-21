package com.cinephilia.payment.services

import org.springframework.stereotype.Component
import java.util.UUID

data class User(val id: UUID);

@Component
class JwtService {
    fun isJwtValid(authHeader: String): Boolean {
        // TODO: implement with user API
        val jwt = authHeader.split(' ')[1]

        return true
    }

    fun extractUser(authHeader: String): User {
        // TODO: implement with user API
        return User(UUID.randomUUID())
    }
}