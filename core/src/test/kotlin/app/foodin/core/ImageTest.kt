package app.foodin.core

import app.foodin.core.service.ImageUploadService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [ImageTest::class])
@SpringBootApplication
class ImageTest {

    @Autowired
    lateinit var imageUploadService: ImageUploadService

    @Test
    fun imageOneUpload() {
    }
}
