package app.foodin.domain.user

import app.foodin.domain.food.Food
import app.foodin.entity.food.FoodEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface FoodGateway {

    fun findAll(spec: Specification<FoodEntity>?, pageable: Pageable) : List<Food>
    fun saveFrom(food: Food) : Food
}