package app.foodin.entity.food

import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

data class FoodFilterQuery(
    val filter: FoodFilter
) : BaseFilterQuery<Food, FoodEntity> {

    override fun toSpecification(): Specification<FoodEntity> = and(
            hasName(filter.name),
            hasTag(filter.tag),
            hasCategoryIdIn(filter.categoryIdList),
            hasSellerNameIn(filter.sellerNameList)
    )
}

fun hasName(name: String?): Specification<FoodEntity>? = name?.let {
    FoodEntity::name.like("%$name%")
}

fun hasCategoryIdIn(categoryIdList: List<Long>?): Specification<FoodEntity>? = categoryIdList?.let {
    FoodEntity::categoryId.`in`(categoryIdList)
}

fun hasSellerNameIn(sellerNameList: List<String>): Specification<FoodEntity>? = sellerNameList?.let {
    or(sellerNameList.map(::hasSellerName))
}

fun hasSellerName(sellerName: String?): Specification<FoodEntity>? = sellerName?.let {
    FoodEntity::sellerNames.like("%$sellerName%")
}

fun hasTag(tag: String?): Specification<FoodEntity>? = tag?.let {
    FoodEntity::tags.like("%$tag%")
}