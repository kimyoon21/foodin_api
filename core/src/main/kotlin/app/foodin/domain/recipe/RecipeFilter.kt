package app.foodin.domain.recipe

import app.foodin.domain.BaseFilter

data class RecipeFilter(val writerId: Long? = null) : BaseFilter()
