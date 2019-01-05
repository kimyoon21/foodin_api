package app.foodin.core.gateway

import app.foodin.core.domain.User

interface UserGateway {
    fun findByEmail(email: String): User?
}