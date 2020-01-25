package app.foodin.entity.foodRegRequest

import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class FoodRegRequestFilterQuery(val filter: FoodRegRequestFilter) : BaseFilterQuery<FoodRegRequest,
        FoodRegRequestEntity> {
    override fun toSpecification(): Specification<FoodRegRequestEntity> = filter.let {
        and(
                equalFilter(FoodRegRequestEntity::writeUserId, it.writeUserId),
                likeFilter(FoodRegRequestEntity::name, it.name, MatchMode.ANYWHERE)
        )
    }
}
