package app.foodin.entity.user

import app.foodin.entity.TestBase
import org.junit.Assert
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

    @Test
    fun `이메일로 검색`() {
        val jpa = JpaUserRepository(userRepository)
        val email = "rutesun@gmail.com"
        val user = jpa.findByEmail(email)
        Assert.assertEquals(user?.email, email)
    }
}
