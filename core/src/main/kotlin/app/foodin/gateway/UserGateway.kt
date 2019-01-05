package app.foodin.gateway

import app.foodin.domain.User

interface UserGateway {
    fun findByEmail(email: String): User?
}