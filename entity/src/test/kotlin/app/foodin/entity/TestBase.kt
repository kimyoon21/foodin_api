package app.foodin.entity

import app.foodin.annotation.KotlinNoArgsConstructor
import app.foodin.entity.config.TestApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
@KotlinNoArgsConstructor
open class TestBase