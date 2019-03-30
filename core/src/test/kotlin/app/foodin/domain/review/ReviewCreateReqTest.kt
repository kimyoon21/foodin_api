package app.foodin.domain.review

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.*
import org.junit.Test

class ReviewCreateReqTest {

    // 일부로 퓨어한 mapper 로 테스트
    private val mapper = ObjectMapper()

    @Test
    fun `json marhsall`() {
        var req = ReviewCreateReq(foodId = 1, writeUserId = 1, review = ReviewReq(rating = 4.1f, contents = "맛없어요."))

        val jsonStr = mapper.writeValueAsString(req)

        assertEquals(
            """{"foodId":1,"writeUserId":1,"rating":4.1,"price":null,"contents":"맛없어요.","tagList":[],"mainImageUri":null,"imageUriList":[]}""".trimIndent(),
            jsonStr
        )

        with(mapper.readValue(jsonStr, ReviewCreateReq::class.java)) {
            assertEquals(req.foodId, foodId)
            assertEquals(req.writeUserId, writeUserId)
            assertEquals(req.review.contents, review.contents)
            assertEquals(req.review.rating, review.rating)
        }
    }
}