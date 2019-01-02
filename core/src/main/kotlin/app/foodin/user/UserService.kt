package app.foodin.service

import app.foodin.domain.User
import app.foodin.user.SnsType
import app.foodin.user.UserRepository
import app.foodin.user.UserSnsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val userSnsRepository: UserSnsRepository
) {
     fun findAll(): List<User> {
        val list = listOf<User>(
                User("mail1", "1", "name1"),
                User("mail22", "2", "name2"),
                User("mail333", "3", "name3")
        )
        return list
    }


     fun findByEmail(email: String): Optional<User> {
         return Optional.of(User(email, "123", "송준현"))
    }

    fun findBySnsTypeAndUid(snsType: SnsType, uid: String) = userSnsRepository.findBySnsTypeAndUid(snsType, uid)
}