package app.foodin.entity.food

import app.foodin.domain.food.Food
import app.foodin.domain.user.FoodGateway
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface FoodRepository : JpaRepository<FoodEntity, Long>, JpaSpecificationExecutor<FoodEntity> {
}

@Component
class JpaFoodRepository(private val foodRepository: FoodRepository) : FoodGateway {
    override fun saveFrom(food: Food): Food {
        return foodRepository.save(FoodEntity(food)).toFood() 
    }

    override fun findAll(spec: Specification<FoodEntity>?, pageable: Pageable): List<Food> {
        val list = foodRepository.findAll(spec,pageable)
        return list.map { x -> x.toFood() }.toCollection(LinkedList())
    }

}