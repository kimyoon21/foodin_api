package app.foodin.entity.food

import app.foodin.common.extension.hasValueLet
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.entity.common.*
import app.foodin.entity.foodCategory.FoodCategoryEntity
import org.springframework.data.jpa.domain.Specification

class FoodFilterQuery(
    val filter: FoodFilter
) : BaseFilterQuery<Food, FoodEntity> {

    override fun toSpecification(): Specification<FoodEntity> = filter.let {
        and(
                equalFilter(FoodEntity::status, it.status),
                hasNameLike(it.name),
                hasTagLike(it.tag),
                inListFilter(FoodEntity::categoryId, it.categoryIdList),
                it.filterNameList.hasValueLet { filterNameList ->
                    querysToSpecification(
                            where { `in`(it.join(FoodEntity::category).get(FoodCategoryEntity::filterName)).apply { filterNameList.forEach { this.value(it) } } }
                    )
                },
                hasSellerNameIn(it.sellerNameList),
                isNotNullFilter(FoodEntity::mainImageUri, it.hasImage),
                it.query.hasValueLet { q ->
                    querysToSpecification(
                            hasNameLike(q),
                            hasTagLike(q),
                            hasSellerName(q),
                            where { equal(it.join(FoodEntity::category).get(FoodCategoryEntity::filterName), q) }
                    )
                }
        )
    }

    companion object {

        fun hasSellerNameIn(sellerNameList: List<String>): Specification<FoodEntity>? = sellerNameList.hasValueLet {
            or(sellerNameList.map(::hasSellerName))
        }

        fun hasSellerName(sellerName: String?): Specification<FoodEntity>? = sellerName.hasValueLet {
            FoodEntity::sellerNames.like("%$sellerName%")
        }

        fun hasNameLike(name: String?): Specification<FoodEntity>? = likeFilter(FoodEntity::name, name, MatchMode.ANYWHERE)

        fun hasTagLike(tag: String?): Specification<FoodEntity>? = likeFilter(FoodEntity::tags, tag, MatchMode.ANYWHERE)
    }
}
