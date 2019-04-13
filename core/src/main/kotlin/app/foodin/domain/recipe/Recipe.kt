package app.foodin.domain.recipe

import app.foodin.domain.common.BaseDomain
import kotlin.Long

data class Recipe(override var id: Long = 0L) : BaseDomain(id)
