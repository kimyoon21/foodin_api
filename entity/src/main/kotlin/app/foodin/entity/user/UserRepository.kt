package app.foodin.entity.user

import app.foodin.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}

class JpaUserRepository(private val userRepository: UserRepository) {
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }
}