package app.foodin.entity.foodCategory

import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.and
import app.foodin.entity.common.equalFilter
import org.springframework.data.jpa.domain.Specification

class FoodCategoryFilterQuery(val filter: FoodCategoryFilter) : BaseFilterQuery<FoodCategory, FoodCategoryEntity> {
    override fun toSpecification(): Specification<FoodCategoryEntity> =
            filter.let { filter -> and(
                    equalFilter(FoodCategoryEntity::detailName, filter.detailName),
                    equalFilter(FoodCategoryEntity::filterName, filter.filterName)

            ) }

}
