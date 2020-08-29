package app.foodin.domain.recipe

import app.foodin.domain.StatusFilter

data class RecipeFilter(
    val writerId: Long? = null,
    val foodId: Long? = null,
    val filterNameList: List<String> = listOf(),
    var categoryIdList: List<Long> = listOf(),
    val hasImage: Boolean? = null
) : StatusFilter()
