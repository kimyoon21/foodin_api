package app.foodin.domain.recipe

import app.foodin.common.enums.Status
import app.foodin.domain.food.FoodInfoDto

data class RecipeInfoDto(

        var id: Long = 0,

        var foodList: List<FoodInfoDto> = listOf(),

        var writeUserId: Long? = null,

        var writeUserNickName: String? = null,

        var status: Status? = null,

        var ratingAvg: Float? = null,

        var mainImageUri: String? = null,

        var tagList: List<String> = listOf(),

        var loveCount: Int = 0,

        var ratingCount: Int = 0,

        var reviewCount: Int = 0,

        var commentCount: Int? = null
)
