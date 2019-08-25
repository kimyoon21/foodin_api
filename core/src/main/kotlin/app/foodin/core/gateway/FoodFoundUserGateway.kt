package app.foodin.core.gateway

import app.foodin.domain.BaseFilter
import app.foodin.domain.food.FoodFoundUser

interface FoodFoundUserGateway : BaseGateway<FoodFoundUser,BaseFilter> {
    fun findAllByFoodId(id: Long) : List<FoodFoundUser>

    fun findAllByUserId(id: Long) : List<FoodFoundUser>
}
