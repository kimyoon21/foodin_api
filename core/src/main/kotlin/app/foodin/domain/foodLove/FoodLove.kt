package app.foodin.domain.foodLove

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.food.Food
import app.foodin.domain.user.User

data class FoodLove(override var id: Long = 0L,
                    val foodId: Long = 0,
                    val userId: Long = 0) : BaseDomain(id) {

    lateinit var food: Food
    lateinit var user: User
}
