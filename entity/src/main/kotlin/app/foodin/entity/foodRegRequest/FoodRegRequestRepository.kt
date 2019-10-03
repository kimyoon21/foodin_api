package app.foodin.entity.foodRegRequest

import app.foodin.core.gateway.FoodRegRequestGateway
import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface FoodRegRequestRepository : BaseRepositoryInterface<FoodRegRequestEntity>

@Component
class JpaFoodRegRequestRepository(private val repository: FoodRegRequestRepository) :
        BaseRepository<FoodRegRequest, FoodRegRequestEntity, FoodRegRequestFilter>(repository),
        FoodRegRequestGateway {
    override fun saveFrom(t: FoodRegRequest): FoodRegRequest {
        return repository.saveAndFlush(FoodRegRequestEntity(t)).toDomain()
    }
}
