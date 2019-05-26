package app.foodin.entity.love

import app.foodin.core.gateway.LoveGateway
import app.foodin.domain.common.EntityType
import app.foodin.domain.love.Love
import app.foodin.domain.love.LoveFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface LoveRepository : BaseRepositoryInterface<LoveEntity>

@Component
class JpaLoveRepository(private val repository: LoveRepository) :
        BaseRepository<Love, LoveEntity, LoveFilter>(repository), LoveGateway {
    /****
     * userId 와 type 을 통해 유저가 해당 food,review 등을 좋아했는지 여부를 체크
     */
    override fun findByUserIdAndEntityTypeAndId(userId: Long, type: EntityType, entityId: Long): List<Love> {
        val entitySpec = when (type) {
            EntityType.FOOD -> LoveEntity::foodId.equal(entityId)
            EntityType.REVIEW -> LoveEntity::reviewId.equal(entityId)
            EntityType.RECIPE -> LoveEntity::recipeId.equal(entityId)
            else -> null
        }
        val spec: Specification<LoveEntity> = and(
                LoveEntity::userId.equal(userId),
                entitySpec)
        return repository.findAll(spec).toDomainList()
    }

    override fun saveFrom(t: Love): Love {
        return repository.saveAndFlush(LoveEntity(t)).toDomain()
    }
}
