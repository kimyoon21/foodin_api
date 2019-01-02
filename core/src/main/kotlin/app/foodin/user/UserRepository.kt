package app.foodin.user

import app.foodin.domain.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {

    fun findByEmail(name: String): Optional<User>

    fun findAllByEmail(name: String): Iterable<User>

}