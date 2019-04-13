package app.foodin.entity.foodLove

import app.foodin.core.gateway.FoodLoveGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.foodLove.FoodLove
import app.foodin.domain.foodLove.FoodLoveFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface FoodLoveRepository : BaseRepositoryInterface<FoodLoveEntity>

@Component
class JpaFoodLoveRepository(private val repository: FoodLoveRepository) : BaseRepository<FoodLove,
        FoodLoveEntity, FoodLoveFilter>(repository), FoodLoveGateway {

    override fun saveFrom(foodLove: FoodLove): FoodLove {
        return repository.saveAndFlush(FoodLoveEntity(foodLove)).toDomain()
    }
}
