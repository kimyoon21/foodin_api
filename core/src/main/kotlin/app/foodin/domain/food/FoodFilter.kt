package app.foodin.domain.food

import app.foodin.domain.BaseFilter

data class FoodFilter(
    val name: String? = null,
    val categoryIdList: List<Long> = listOf(),
    val tag: String? = null,
    val sellerNameList: List<String> = listOf(),
    val hasImage : Boolean? = null
) : BaseFilter()
