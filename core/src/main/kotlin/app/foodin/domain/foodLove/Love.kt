package app.foodin.domain.foodLove

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.food.Food
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.review.Review
import app.foodin.domain.user.User

data class Love(override var id: Long = 0L,
                val foodId: Long? = null,
                val reviewId: Long? = null,
                val recipeId: Long? = null,
                val userId: Long = 0) : BaseDomain(id) {

    var food: Food? = null
    var review: Review? = null
    var recipe: Recipe? = null
    lateinit var user: User
}
