package app.foodin.service

import app.foodin.domain.User
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User
}

@Service
class DefaultUserService: UserService {
    override fun findByEmail(email: String): User {
        return User(email, "송준현")
    }
}