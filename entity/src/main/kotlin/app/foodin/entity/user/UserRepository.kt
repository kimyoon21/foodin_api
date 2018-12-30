package app.foodin.entity.user

import app.foodin.user.domain.User
import app.foodin.user.gateway.UserGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}

class UserJpaRepository(
    private val userRepository: UserRepository
) : UserGateway {
//    @Autowired
//    private lateinit var userRepository: UserRepository

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }
}