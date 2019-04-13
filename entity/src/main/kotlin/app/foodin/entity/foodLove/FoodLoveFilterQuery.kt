package app.foodin.entity.foodLove

import app.foodin.domain.foodLove.FoodLove
import app.foodin.domain.foodLove.FoodLoveFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.and
import app.foodin.entity.common.equalFilter
import org.springframework.data.jpa.domain.Specification

class FoodLoveFilterQuery(
    val filter: FoodLoveFilter
) : BaseFilterQuery<FoodLove, FoodLoveEntity> {

    override fun toSpecification(): Specification<FoodLoveEntity> = filter.let {
        and(
                equalFilter(FoodLoveEntity::foodId,it.foodId),
                equalFilter(FoodLoveEntity::userId,it.userId)
        )
    }

}
