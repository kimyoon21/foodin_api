package app.foodin.core.gateway

import app.foodin.domain.BaseFilter
import app.foodin.domain.foodCategory.FoodCategory
import org.springframework.data.domain.Page

interface FoodCategoryGateway : BaseGateway<FoodCategory, BaseFilter> {
    fun findByFilterName(name: String): Page<FoodCategory>
}
