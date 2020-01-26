package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ALREADY_EXISTS_WHAT
import app.foodin.common.exception.EX_NEED
import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeCreateReq
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.domain.review.ReviewReq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeService(override val gateway: RecipeGateway,
                    val foodService: FoodService,
                    val userService: UserService) : BaseService<Recipe, RecipeFilter>() {

    fun update(reviewId: Long, req: ReviewReq): Recipe {
        val review = gateway.findById(reviewId) ?: throw CommonException(EX_ALREADY_EXISTS_WHAT, "word.review")
        return saveFrom(review)
    }

    fun save(createReq: RecipeCreateReq): Recipe {
        if (createReq.recipeReq.foodIdList.isEmpty()) {
            throw CommonException(EX_NEED,"food id")
        }
        val foodList = createReq.recipeReq.foodIdList.map { foodService.findById(it) }

        val writer = userService.findById(createReq.writeUserId)

        return saveFrom(Recipe(foodList, writer, recipeReq = createReq.recipeReq))
    }

}
