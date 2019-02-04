package app.foodin.domain.user

import app.foodin.domain.food.Food
import app.foodin.entity.food.FoodEntity
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

interface UserWritableService{
    fun findAll(spec:Specification<FoodEntity>?,pageable: Pageable): List<Food>
    fun saveFrom(food: Food) : Food
}

@Service
class FoodService(
        private val foodGateway: FoodGateway
) : UserWritableService{
    override fun saveFrom(food: Food): Food {
        return foodGateway.saveFrom(food)
    }

    private val logger = LoggerFactory.getLogger(FoodService::class.java)

    override fun findAll(spec: Specification<FoodEntity>?, pageable: Pageable): List<Food> {
        return foodGateway.findAll(spec,pageable)
    }

}