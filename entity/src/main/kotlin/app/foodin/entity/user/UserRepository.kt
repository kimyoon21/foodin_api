package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import app.foodin.domain.user.UserGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}

@Component
class JpaUserRepository(private val userRepository: UserRepository) : UserGateway {
    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }


}