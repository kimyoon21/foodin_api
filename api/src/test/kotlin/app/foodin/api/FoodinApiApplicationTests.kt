package app.foodin.api

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class FoodinApiApplicationTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `email 조회 API`() {
        val mvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/user").param("email", "rutesun@gmail.com")

        )
            .andDo { println() }
            .andExpect { MockMvcResultMatchers.status().is2xxSuccessful }
            .andReturn()

        var content = mvcResult.response.contentAsString
        println(content)
    }
}
