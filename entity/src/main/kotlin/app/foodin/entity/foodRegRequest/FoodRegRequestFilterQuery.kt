package app.foodin.entity.foodRegRequest

import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter
import app.foodin.entity.common.BaseFilterQuery
import org.springframework.data.jpa.domain.Specification

class FoodRegRequestFilterQuery(val filter: FoodRegRequestFilter) : BaseFilterQuery<FoodRegRequest,
        FoodRegRequestEntity> {
    override fun toSpecification(): Specification<FoodRegRequestEntity> {
                TODO("not implemented")
    }
}
