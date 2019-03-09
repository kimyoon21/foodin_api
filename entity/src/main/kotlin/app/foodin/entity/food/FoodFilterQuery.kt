package app.foodin.entity.food

import app.foodin.common.extension.hasValueLet
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class FoodFilterQuery(
    val filter: FoodFilter
) : BaseFilterQuery<Food, FoodEntity> {

    override fun toSpecification(): Specification<FoodEntity> = filter.let {
        and(
                hasNameLike(it.name),
                hasTagLike(it.tag),
                inListFilter(FoodEntity::categoryId, it.categoryIdList),
                hasSellerNameIn(it.sellerNameList),
                querysToSpecification(
                        hasNameLike(it.query),
                        hasTagLike(it.query),
                        hasSellerName(it.query)
                )
        )
    }

    companion object {

        fun hasSellerNameIn(sellerNameList: List<String>): Specification<FoodEntity>? = sellerNameList.hasValueLet {
            or(sellerNameList.map(::hasSellerName))
        }

        fun hasSellerName(sellerName: String?): Specification<FoodEntity>? = sellerName.hasValueLet {
            FoodEntity::sellerNames.like("%$sellerName%")
        }

        fun hasNameLike(name: String?): Specification<FoodEntity>? = likeFilter(FoodEntity::name, name, MathMode.ANYWHERE)

        fun hasTagLike(tag: String?): Specification<FoodEntity>? = likeFilter(FoodEntity::tags, tag, MathMode.ANYWHERE)
    }
}
