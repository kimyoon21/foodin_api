package app.foodin.entity

import app.foodin.entity.config.TestApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [TestApplication::class],
    properties = ["classpath:entity.properties"]
)
open class TestBase
