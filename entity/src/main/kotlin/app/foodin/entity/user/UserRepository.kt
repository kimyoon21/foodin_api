package app.foodin.entity.user

import app.foodin.domain.User
import app.foodin.gateway.UserGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}

@Component
class JpaUserRepository(private val userRepository: UserRepository) : UserGateway {
    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }
}