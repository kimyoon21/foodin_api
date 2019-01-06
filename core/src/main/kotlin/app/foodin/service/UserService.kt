package app.foodin.service

import app.foodin.domain.User
import app.foodin.gateway.UserGateway
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User,
    fun findBySnsTypeAndUid(snsType:SnsType, )
}

@Service
class DefaultUserService(private val userGateway: UserGateway): UserService {
    override fun findByEmail(email: String): User {
        return userGateway.findByEmail(email)!!
    }

    override fun findBySnsTypeAndUid(email: String): User {
        return userGateway.findByEmail(email)!!
    }



}