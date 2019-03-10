package app.foodin.core.gateway

import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FoodGateway : BaseGateway<Food, FoodFilter> {
    fun findByName(name: String): Food?
    fun findNameAll(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDTO>?
}