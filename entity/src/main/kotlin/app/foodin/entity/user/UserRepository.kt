package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import app.foodin.domain.user.SessionLogGateway
import app.foodin.domain.user.UserGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): UserEntity?
}

@Component
class JpaUserRepository(private val userRepository: UserRepository) : UserGateway {
    override fun saveFrom(user: User): User? {
        return userRepository.save(UserEntity(user))?.toUser()
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userRepository.findBySnsTypeAndSnsUserId(snsType, uid)?.toUser()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }


}