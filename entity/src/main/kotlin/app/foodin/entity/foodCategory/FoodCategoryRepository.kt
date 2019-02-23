package app.foodin.entity.foodCategory

import app.foodin.core.gateway.FoodCategoryGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository


@Repository
interface FoodCategoryRepository : JpaRepository<FoodCategoryEntity, Long>, JpaSpecificationExecutor<FoodCategoryEntity> {

}

@Component
class JpaFoodCategoryRepository(private val foodCategoryRepository: FoodCategoryRepository) : FoodCategoryGateway {
//    override fun findById(id: Long): Food? {
//        return foodCategoryRepository.findById(id).orElse(null)?.toDomain()
//    }
//
//    override fun saveFrom(t: Food): Food {
//        return foodCategoryRepository.saveAndFlush(FoodEntity(t)).toDomain()
//    }
//
//    override fun findAll(spec: Specification<*>?, pageable: Pageable): Page<Food> {
//        @Suppress("UNCHECKED_CAST")
//        spec as (Specification<FoodEntity>)
//
//        return foodCategoryRepository.findAll(spec,pageable).map(FoodEntity::toFood)
//    }

}