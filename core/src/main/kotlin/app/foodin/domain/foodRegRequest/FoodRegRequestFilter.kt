package app.foodin.domain.foodRegRequest

import app.foodin.domain.BaseFilter

data class FoodRegRequestFilter(
    var name: String? = null,
    var writeUserId: Long? = null
) : BaseFilter()
