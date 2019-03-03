package app.foodin.domain.user

import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface FoodGateway : BaseGateway<Food> {
    fun findByName(name: String) : Food?
    fun findNameAll(spec: Specification<*>?, pageable: Pageable): Page<FoodDto>?
}