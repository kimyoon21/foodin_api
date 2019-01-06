package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import app.foodin.entity.session.SessionLogEntity
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(userRegisterDTO: UserRegisterDTO): User
    fun loggedIn(user: User)
}

@Service
class DefaultUserService(
        private val userGateway: UserGateway,
        private val sessionLogGateway: SessionLogGateway
) : UserService {
    override fun loggedIn(user: User) {


        val jwtToken = "temp"

        sessionLogGateway.save(SessionLogEntity(userId = user.id!!, jwtToken = jwtToken))
    }

    override fun saveFrom(userRegisterDTO: UserRegisterDTO): User {
        return userGateway.saveFrom(userRegisterDTO.toUser())!!
    }

    override fun findByEmail(email: String): User {
        return userGateway.findByEmail(email)!!
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userGateway.findBySnsTypeAndSnsUserId(snsType, uid)
    }


}