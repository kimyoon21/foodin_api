package app.foodin.entity.foodCategory

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository


@Repository
interface FoodCategoryRepository : BaseRepositoryInterface<FoodCategoryEntity> {
    fun findByFilterName(filterName: String): FoodCategoryEntity?
}


@Component
class JpaFoodCategoryRepository(private val foodCategoryRepository: FoodCategoryRepository)
    : BaseRepository<FoodCategory,FoodCategoryEntity>(foodCategoryRepository), FoodCategoryGateway {
    override fun findByFilterName(filterName: String): FoodCategory? {
        return foodCategoryRepository.findByFilterName(filterName)?.toDomain()
    }

    override fun saveFrom(t: FoodCategory): FoodCategory {
        return foodCategoryRepository.saveAndFlush(FoodCategoryEntity(t)).toDomain()
    }

}