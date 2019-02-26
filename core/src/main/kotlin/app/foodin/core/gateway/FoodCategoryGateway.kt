package app.foodin.core.gateway

import app.foodin.domain.foodCategory.FoodCategory

interface FoodCategoryGateway : BaseGateway<FoodCategory> {
    fun findByFilterName(name: String) : FoodCategory?
}
