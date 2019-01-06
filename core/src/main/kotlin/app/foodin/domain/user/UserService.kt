package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User
    fun findBySnsTypeAndUid(snsType: SnsType, uid: String): User
    fun saveFrom(userRegisterDTO: UserRegisterDTO): User
}

@Service
class DefaultUserService(private val userGateway: UserGateway) : UserService {
    override fun saveFrom(userRegisterDTO: UserRegisterDTO): User {
        return userGateway.save(userRegisterDTO.toUser())!!
    }

    override fun findByEmail(email: String): User {
        return userGateway.findByEmail(email)!!
    }

    override fun findBySnsTypeAndUid(snsType: SnsType, uid: String): User {
        return userGateway.findBySnsTypeAndSnsUserId(snsType, uid)!!
    }


}