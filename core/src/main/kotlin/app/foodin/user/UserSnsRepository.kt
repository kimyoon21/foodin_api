package app.foodin.user

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserSnsRepository : PagingAndSortingRepository<UserSns, Long> {

    fun findBySnsTypeAndUid(snsType: SnsType, uid: String): Optional<UserSns>
}
