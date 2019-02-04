package app.foodin.domain.user

import app.foodin.domain.food.Food
import org.springframework.data.domain.Pageable

interface FoodGateway {

    fun findAll(pageable: Pageable) : List<Food>
    fun saveFrom(food: Food) : Food
}