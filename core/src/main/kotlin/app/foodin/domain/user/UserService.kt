package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.sessionLog.SessionLog
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

interface UserService {
    fun findByEmail(email: String): User
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(userRegisterDTO: UserRegisterDTO): User
    fun loggedIn(user: User)
}

@Service
class CustomUserDetailsService(
        private val userGateway: UserGateway,
        private val sessionLogGateway: SessionLogGateway
) : UserService, UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        return userGateway.findByEmail(username?:"") ?:
         throw UsernameNotFoundException("User not found")
    }

    override fun loggedIn(user: User) {


        val jwtToken = "temp"

        sessionLogGateway.saveFrom(SessionLog(userId = user.id!!, jwtToken = jwtToken))
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