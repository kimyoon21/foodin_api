package app.foodin.domain.recipe

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

@KotlinNoArgConstructor
data class RecipeCreateReq(
        val writeUserId : Long,
        var recipeReq: RecipeReq
) : Serializable

@KotlinNoArgConstructor
data class ReviewUpdateReq(

        var recipeReq: RecipeReq
) : Serializable

@KotlinNoArgConstructor
data class RecipeReq(
        val name: String,
        val foodIdList: MutableList<Long> = mutableListOf(),
        val contents: String,
        val tagList: MutableList<String> = mutableListOf(),
        val mainImageUri: String,
        val imageUriList: MutableList<String> = mutableListOf()
)
