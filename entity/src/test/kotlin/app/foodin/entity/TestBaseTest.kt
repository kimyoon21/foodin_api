package app.foodin.entity

import app.foodin.entity.user.UserRepository
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class TestBaseTest : TestBase() {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun `context check`() {
        assertNotNull(applicationContext.getBean(UserRepository::class.java))
    }
}