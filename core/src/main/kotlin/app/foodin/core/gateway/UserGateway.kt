package app.foodin.core.gateway

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import app.foodin.domain.user.UserUpdateReq

interface UserGateway {
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(user: User): User
    fun updateFrom(userId: Long, req: UserUpdateReq): User
    fun findAll(): List<User>
    fun findById(id: Long): User?
    fun findByNickName(nickName: String): User?
    fun findByLoginId(loginId: String): User?
    fun updateEnabled(userId: Long, enabled: Boolean): Int
}
