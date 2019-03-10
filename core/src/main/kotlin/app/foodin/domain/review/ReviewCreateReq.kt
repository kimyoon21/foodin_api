package app.foodin.domain.review

import app.foodin.domain.food.Food
import app.foodin.domain.user.User
import java.io.Serializable

/***
 * 리뷰 (평점) 등록용
 */
data class ReviewCreateReq(
    val foodId: Long,
    val writeUserId: Long,
    val rating: Float,
    val price: Int? = null,
    val contents: String? = null,
    val tagList: MutableList<String> = mutableListOf(),
    val mainImageUri: String? = null,
    val imageUriList: MutableList<String> = mutableListOf()

) : Serializable {

    lateinit var food: Food
    lateinit var writeUser: User
}
