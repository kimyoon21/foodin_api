package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.core.gateway.UserGateway
import app.foodin.domain.user.User
import app.foodin.domain.user.UserUpdateReq
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByLoginId(logindId: String): UserEntity?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): UserEntity?
}

@Component
class JpaUserRepository(private val userRepository: UserRepository) : UserGateway {
    override fun findByLoginId(loginId: String): User? {
        return userRepository.findByLoginId(loginId)?.toDomain()
    }

    override fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun findAll(): List<User> {
//        Pageable  TODO page 처리 나중에
        val list = userRepository.findAll()
        return list.map { x -> x.toDomain() }.toCollection(LinkedList())
    }

    override fun saveFrom(user: User): User {
        return userRepository.save(UserEntity(user)).toDomain()
    }

    override fun updateFrom(userId: Long, req: UserUpdateReq): User {

        val userEntity = userRepository.findById(userId).orElseThrow { throw CommonException(EX_NOT_EXISTS, "word.user") }
        userEntity.mergeFromUpdateReq(req)
        return userEntity.toDomain()
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userRepository.findBySnsTypeAndSnsUserId(snsType, uid)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toDomain()
    }
}