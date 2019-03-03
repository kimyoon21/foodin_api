package app.foodin.domain.user

import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.food.Food

interface FoodGateway : BaseGateway<Food> {
    fun findByName(name: String): Food?
}