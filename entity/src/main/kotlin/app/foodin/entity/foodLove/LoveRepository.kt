package app.foodin.entity.foodLove

import app.foodin.core.gateway.LoveGateway
import app.foodin.domain.foodLove.Love
import app.foodin.domain.foodLove.LoveFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface LoveRepository : BaseRepositoryInterface<LoveEntity>

@Component
class JpaLoveRepository(private val repository: LoveRepository) :
        BaseRepository<Love, LoveEntity, LoveFilter>(repository), LoveGateway {

    override fun saveFrom(t: Love): Love {
        return repository.saveAndFlush(LoveEntity(t)).toDomain()
    }
}
