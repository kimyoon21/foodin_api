package app.foodin.domain.review

import app.foodin.core.annotation.KotlinNoArgConstructor
import com.fasterxml.jackson.annotation.JsonUnwrapped
import java.io.Serializable

/***
 * 리뷰 (평점) 등록용
 */
@KotlinNoArgConstructor
data class ReviewCreateReq(
    val foodId: Long,
    val writeUserId: Long,

    @field:JsonUnwrapped
    var review: ReviewReq
) : Serializable

@KotlinNoArgConstructor
data class ReviewUpdateReq(

    @field:JsonUnwrapped
    var review: ReviewReq
) : Serializable

@KotlinNoArgConstructor
data class ReviewReq(
    val rating: Float,
    val price: Int? = null,
    val contents: String? = null,
    val tagList: MutableList<String> = mutableListOf(),
    val mainImageUri: String? = null,
    val imageUriList: MutableList<String> = mutableListOf()
)