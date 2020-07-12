package app.foodin.core.gateway

import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import org.springframework.data.domain.Page

interface FoodCategoryGateway : BaseGateway<FoodCategory, FoodCategoryFilter> {
    fun findByFilterName(filterName: String): Page<FoodCategory>
}
