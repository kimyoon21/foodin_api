package app.foodin.domain.food

import app.foodin.domain.StatusFilter

data class FoodFilter(
    val name: String? = null,
    val categoryIdList: List<Long> = listOf(),
    val tag: String? = null,
    val sellerNameList: List<String> = listOf(),
    val hasImage : Boolean? = null
) : StatusFilter()
