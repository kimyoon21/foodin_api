package app.foodin.domain.user

import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodDto
import app.foodin.domain.food.FoodFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FoodGateway : BaseGateway<Food, FoodFilter> {
    fun findByName(name: String): Food?
    fun findNameAll(filter: FoodFilter, pageable: Pageable): Page<FoodDto>?
}