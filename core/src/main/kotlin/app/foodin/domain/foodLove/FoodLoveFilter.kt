package app.foodin.domain.foodLove

import app.foodin.domain.BaseFilter

data class FoodLoveFilter(val userId: Long? = null, val foodId: Long? = null) : BaseFilter()
