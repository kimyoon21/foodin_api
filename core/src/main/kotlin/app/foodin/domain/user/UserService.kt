package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.utils.USERNAME_SEPERATOR
import app.foodin.common.utils.getNDayAgo
import app.foodin.domain.sessionLog.SessionLog
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(userRegisterDTO: UserRegisterDTO): User
    fun loggedIn(user: User,token:String): UserLoginResultDTO
    fun findAll() : List<User>
}

@Service
class CustomUserDetailsService(
        private val userGateway: UserGateway,
        private val sessionLogGateway: SessionLogGateway
) : UserService, UserDetailsService{
    override fun findAll(): List<User> {
        return userGateway.findAll()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw CommonException(EX_NEED)
        val snsType : SnsType = SnsType.valueOf(username.split(USERNAME_SEPERATOR)[0]?.toUpperCase())
        val snsUserId = username.replaceFirst(snsType.name+ USERNAME_SEPERATOR, "")

        return userGateway.findBySnsTypeAndSnsUserId(snsType,snsUserId) ?:
         throw UsernameNotFoundException("User not found")
    }

    override fun loggedIn(user: User, token : String) : UserLoginResultDTO {

        sessionLogGateway.saveFrom(SessionLog(userId = user.id!!, token = token))

        return UserLoginResultDTO(user,token, getNDayAgo(-5))
    }

    override fun saveFrom(userRegisterDTO: UserRegisterDTO): User {
        return userGateway.saveFrom(userRegisterDTO.toUser())!!
    }

    override fun findByEmail(email: String): User? {
        return userGateway.findByEmail(email)
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userGateway.findBySnsTypeAndSnsUserId(snsType, uid)
    }


}