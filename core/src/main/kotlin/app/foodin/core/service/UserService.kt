package app.foodin.core.service

import app.foodin.core.domain.User
import app.foodin.core.gateway.UserGateway
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User
}

@Service
class DefaultUserService(private val userGateway: UserGateway) : UserService {
    override fun findByEmail(email: String): User {
        return userGateway.findByEmail(email)!!
    }
}