package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.core.gateway.UserGateway
import app.foodin.domain.user.User
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
        return list.map { x -> x.toDomain() }.toCollection(LinkedList())
    }

    override fun saveFrom(user: User): User {
        return userRepository.save(UserEntity(user)).toDomain()
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userRepository.findBySnsTypeAndSnsUserId(snsType, uid)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toDomain()
    }
}