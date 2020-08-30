package app.foodin.domain.food

import app.foodin.core.annotation.KotlinNoArgConstructor

@KotlinNoArgConstructor
data class FoodInfoDto(
    var id: Long,
    var name: String,
    var mainImageUri: String?,
    var ratingAvg: Float?,
    var tagList: List<String> = listOf()
) {
    constructor(food: Food) : this(food.id, food.name, food.mainImageUri, food.ratingAvg, food.tagList)
}
