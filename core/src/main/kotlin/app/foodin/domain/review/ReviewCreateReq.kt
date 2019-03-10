package app.foodin.domain.review

import java.io.Serializable

data class ReviewCreateReq(
    val name: String,
    val foodId: Long

) : Serializable
