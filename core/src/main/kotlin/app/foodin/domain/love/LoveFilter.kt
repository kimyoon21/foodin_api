package app.foodin.domain.love

import app.foodin.domain.BaseFilter
import app.foodin.domain.common.EntityType

data class LoveFilter(
    var foodId: Long? = null,
    var reviewId: Long? = null,
    var recipeId: Long? = null,
    var type: EntityType? = null,
    var userId: Long? = null
) : BaseFilter() {
    constructor(loveReq: LoveReq) : this(
            foodId = loveReq.foodId,
            reviewId = loveReq.reviewId,
            recipeId = loveReq.recipeId,
            userId = loveReq.userId
    )
}
