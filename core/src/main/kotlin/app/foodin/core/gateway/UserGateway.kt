package app.foodin.core.gateway

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User

interface UserGateway {
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(user: User): User
    fun findAll(): List<User>
}