package app.foodin.domain.food

import app.foodin.core.annotation.KotlinNoArgConstructor

@KotlinNoArgConstructor
data class FoodInfoDTO(
    var name: String,
    var mainImageUri: String?,
    var ratingAvg: Float?,
    var tagList: List<String> = listOf()
) {
    constructor(food: Food) : this(food.name, food.mainImageUri, food.ratingAvg, food.tagList)
}