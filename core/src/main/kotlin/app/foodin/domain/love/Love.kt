package app.foodin.domain.love

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.food.Food
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.review.Review
import app.foodin.domain.user.User

data class Love(
    override var id: Long = 0L,
    val foodId: Long? = null,
    val reviewId: Long? = null,
    val recipeId: Long? = null,
    val userId: Long
) : BaseDomain(id) {

    var food: Food? = null
    var review: Review? = null
    var recipe: Recipe? = null
    var user: User? = null

    constructor(loveReq: LoveReq) : this(
            foodId = loveReq.foodId,
            reviewId = loveReq.reviewId,
            recipeId = loveReq.recipeId,
            userId = loveReq.userId!!
    )
}
