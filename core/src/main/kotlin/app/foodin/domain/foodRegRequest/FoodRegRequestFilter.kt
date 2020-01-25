package app.foodin.domain.foodRegRequest

import app.foodin.domain.BaseFilter

data class FoodRegRequestFilter(
        val name: String? = null,
        val writeUserId: Long? = null
) : BaseFilter()
