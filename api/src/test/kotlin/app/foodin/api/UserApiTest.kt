package app.foodin.api

import app.foodin.common.enums.Gender
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback
class UserApiTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `kakao 가입`() {
        val mvcResult = mvc.perform(
            MockMvcRequestBuilders.post("/user/register")
                    .param("email", "kimyoon21@naver.com")
                    .param("gender", Gender.MALE.name)
                    .param("birthday", "19870207")
                    .param("agreePolicy", "true")
                    .param("agreeMarketing", "false")
                    .param("snsType", "NAVER")
                    .param("snsUserId", "41315747")
                    .param("realName", "김윤혁")
                    .param("nickName", "킴윤21")

        )
            .andDo { println() }
            .andExpect { MockMvcResultMatchers.status().is2xxSuccessful }
            .andReturn()

        var content = mvcResult.response.contentAsString
        println(content)
    }

    @Test
    fun `kakao 로그인`() {
        val mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/user/login/sns")
                        .param("snsType", "KAKAO")
                        .param("snsUserId", "1002943991")
                        .param("accessToken", "")

        )
                .andDo { println() }
                .andExpect { MockMvcResultMatchers.status().is2xxSuccessful }
                .andReturn()

        var content = mvcResult.response.contentAsString
        println(content)
    }
}
