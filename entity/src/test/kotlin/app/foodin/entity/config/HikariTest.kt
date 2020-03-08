package app.foodin.entity.config

import javax.sql.DataSource
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
    classes = [TestApplication::class]
)
class HikariTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun hikariConnectionPoolIsConfigured() {
        assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource::class.java.name)
    }
}
