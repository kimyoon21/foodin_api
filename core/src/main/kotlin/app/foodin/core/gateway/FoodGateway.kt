package app.foodin.core.gateway

import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FoodGateway : BaseGateway<Food, FoodFilter> {
    fun findByName(name: String): Food?
    fun findDtoBy(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDto>?
    fun addLoveCount(id: Long,count:Int)
    fun addRatingCount(id: Long,count:Int)
    fun addReviewCount(id: Long,count:Int)
}