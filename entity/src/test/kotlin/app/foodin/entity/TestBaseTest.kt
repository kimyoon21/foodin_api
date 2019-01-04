package app.foodin.entity

import app.foodin.entity.user.UserRepository
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class TestBaseTest : TestBase() {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var environment: Environment

    @Test
    fun `context check`() {
        assertNotNull(applicationContext.getBean(UserRepository::class.java))
        assertNotNull(environment.getProperty("spring.datasource.url"))
    }
}