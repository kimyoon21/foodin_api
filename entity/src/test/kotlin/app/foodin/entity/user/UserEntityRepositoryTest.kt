package app.foodin.entity.user

import app.foodin.entity.TestBase
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class UserEntityRepositoryTest : TestBase() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `simple`() {
        val all = userRepository.findAll()
        println(all)
    }
}