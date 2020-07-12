package app.foodin.entity.foodCategory

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.toDomainList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface FoodCategoryRepository : BaseRepositoryInterface<FoodCategoryEntity> {
    fun findByFilterName(filterName: String, pageable: Pageable): Page<FoodCategoryEntity>
}

@Component
class JpaFoodCategoryRepository(private val foodCategoryRepository: FoodCategoryRepository) :
    BaseRepository<FoodCategory, FoodCategoryEntity, FoodCategoryFilter>(foodCategoryRepository), FoodCategoryGateway {

    override fun findByFilterName(filterName: String): Page<FoodCategory> {
        return foodCategoryRepository.findByFilterName(
                filterName,
                Pageable.unpaged()
        ).toDomainList()
    }

    override fun findAllByFilter(filter: FoodCategoryFilter, pageable: Pageable): Page<FoodCategory> {
        return findAll(FoodCategoryFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: FoodCategory): FoodCategory {
        return foodCategoryRepository.saveAndFlush(FoodCategoryEntity(t)).toDomain()
    }
}
