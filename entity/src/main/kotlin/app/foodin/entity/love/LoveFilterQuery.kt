package app.foodin.entity.love

import app.foodin.domain.common.EntityType
import app.foodin.domain.love.Love
import app.foodin.domain.love.LoveFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class LoveFilterQuery(
    val filter: LoveFilter
) : BaseFilterQuery<Love, LoveEntity> {

    override fun toSpecification(): Specification<LoveEntity> = filter.let {
        and(
                hasEntityType(it.type),
                equalFilter(LoveEntity::foodId, it.foodId),
                equalFilter(LoveEntity::reviewId, it.reviewId),
                equalFilter(LoveEntity::recipeId, it.recipeId),
                equalFilter(LoveEntity::userId, it.userId)
        )
    }

    companion object {

        fun hasEntityType(entityType: EntityType?): Specification<LoveEntity>? {
            return when (entityType) {
                EntityType.FOOD -> LoveEntity::foodId.isNotNull()
                EntityType.REVIEW -> LoveEntity::reviewId.isNotNull()
                EntityType.RECIPE -> LoveEntity::recipeId.isNotNull()
                else -> emptySpecification()
            }
        }
    }
}
