package app.foodin.entity.food

import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

/**
 * A TV show query DTO - typically used at the service layer.
 */
data class FoodFilter(
        val name: String? = null,
        val categoryIdList: List<Long> = listOf(),
        val tag: String? = null,
        val sellerNameList: List<String> = listOf()
){
    fun toSpecification(): Specification<FoodEntity> = and(
            hasName(name),
            hasTag(tag),
            hasCategoryIdIn(categoryIdList),
            hasSellerNameIn(sellerNameList)
    )
}


fun hasName(name: String?): Specification<FoodEntity>? = name?.let {
    FoodEntity::name.equal(it)
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