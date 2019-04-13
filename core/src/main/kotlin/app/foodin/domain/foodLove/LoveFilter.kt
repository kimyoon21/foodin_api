package app.foodin.domain.foodLove

import app.foodin.domain.BaseFilter
import app.foodin.domain.common.EntityType

data class LoveFilter(var foodId: Long? = null,
                      var reviewId: Long? = null,
                      var recipeId: Long? = null,
                      var type : EntityType? = null,
                      var userId: Long? = null) : BaseFilter()
