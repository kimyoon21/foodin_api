package app.foodin.domain.user

import app.foodin.domain.food.Food
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface FoodGateway {

    fun findAll(spec: Specification<*>?, pageable: Pageable) : Page<Food>
    fun saveFrom(food: Food) : Food
}