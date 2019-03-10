package app.foodin.domain.food

data class FoodInfoDTO(
    var name: String,
    var mainImageUri: String?,
    var ratingAvg: Float?,
    var tagList: List<String> = listOf()
) {
    constructor(food: Food) : this(food.name, food.mainImageUri, food.ratingAvg, food.tagList)
}