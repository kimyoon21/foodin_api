package app.foodin.domain.foodLove

data class LoveReq(
                   var foodId: Long? = null,
                   var reviewId: Long? = null,
                   var recipeId: Long? = null,
                   var userId: Long? = null)
