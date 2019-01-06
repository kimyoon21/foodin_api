package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User

interface UserGateway {
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType : SnsType, uid: String) : User?
    fun save(user: User): User?
}