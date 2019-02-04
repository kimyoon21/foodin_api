package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import app.foodin.domain.user.UserGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): UserEntity?
}

@Component
class JpaUserRepository(private val userRepository: UserRepository) : UserGateway {
    override fun findAll(): List<User> {
//        Pageable  TODO page 처리 나중에
        val list = userRepository.findAll()
        return list.map { x -> x.toUser() }.toCollection(LinkedList())
    }

    override fun saveFrom(user: User): User {
        return userRepository.save(UserEntity(user)).toUser()
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userRepository.findBySnsTypeAndSnsUserId(snsType, uid)?.toUser()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }


}