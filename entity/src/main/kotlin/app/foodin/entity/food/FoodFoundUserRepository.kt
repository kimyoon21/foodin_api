package app.foodin.entity.food

import app.foodin.core.gateway.FoodFoundUserGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.food.FoodFoundUser
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.toDomainList
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface FoodFoundUserRepository : BaseRepositoryInterface<FoodFoundUserEntity> {
    fun findAllByFoodId(@Param("id") id: Long): List<FoodFoundUserEntity>

    fun findAllByUserId(@Param("id") id: Long): List<FoodFoundUserEntity>
}

@Component
class JpaFoodFoundUserRepository(private val repository: FoodFoundUserRepository) :
        BaseRepository<FoodFoundUser, FoodFoundUserEntity, BaseFilter>(repository), FoodFoundUserGateway {
    override fun findAllByUserId(id: Long): List<FoodFoundUser> {
        return repository.findAllByFoodId(id).toDomainList()
    }

    override fun findAllByFoodId(id: Long): List<FoodFoundUser> {
        return repository.findAllByFoodId(id).toDomainList()
    }

    override fun saveFrom(t: FoodFoundUser): FoodFoundUser {
        return repository.saveAndFlush(FoodFoundUserEntity(t)).toDomain()
    }
}