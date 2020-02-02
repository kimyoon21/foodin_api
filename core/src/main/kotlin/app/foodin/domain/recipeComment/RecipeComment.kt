package app.foodin.domain.recipeComment

import app.foodin.domain.common.BaseDomain
import kotlin.Long
import kotlin.String

data class RecipeComment(override var id: Long = 0L, field1: String) : BaseDomain(id) {
    var field1: String
}
