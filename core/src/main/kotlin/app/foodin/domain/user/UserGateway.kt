package app.foodin.domain.user

import app.foodin.common.enums.SnsType

interface UserGateway {
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType : SnsType, uid: String) : User?
    fun saveFrom(user: User): User
    fun findAll() : List<User>
}