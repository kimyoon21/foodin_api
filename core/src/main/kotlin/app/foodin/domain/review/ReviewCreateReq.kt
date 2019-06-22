package app.foodin.domain.review

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

/***
 * 리뷰 (평점) 등록용
 */
@KotlinNoArgConstructor
data class ReviewCreateReq(
    val foodId: Long,
    val writeUserId: Long,

    var reviewReq: ReviewReq
) : Serializable

@KotlinNoArgConstructor
data class ReviewUpdateReq(

    var reviewReq: ReviewReq
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