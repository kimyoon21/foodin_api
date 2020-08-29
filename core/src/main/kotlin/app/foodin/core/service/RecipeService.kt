package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ALREADY_EXISTS_WHAT
import app.foodin.common.exception.EX_NEED
import app.foodin.common.extension.hasValueLet
import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeCreateReq
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.domain.recipe.RecipeInfoDto
import app.foodin.domain.review.ReviewReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class RecipeService(
    override val gateway: RecipeGateway,
    val foodService: FoodService,
    val userService: UserService,
    val foodCategoryService: FoodCategoryService
) : StatusService<Recipe, RecipeFilter>() {

    fun update(reviewId: Long, req: ReviewReq): Recipe {
        val review = gateway.findById(reviewId) ?: throw CommonException(EX_ALREADY_EXISTS_WHAT, "word.review")
        return saveFrom(review)
    }

    fun save(createReq: RecipeCreateReq): Recipe {
        if (createReq.recipeReq.foodIdList.isEmpty()) {
            throw CommonException(EX_NEED, "food id")
        }
        val foodList = createReq.recipeReq.foodIdList.map { foodService.findById(it) }

        val writer = userService.findById(createReq.writeUserId)

        return saveFrom(Recipe(foodList, writer, recipeReq = createReq.recipeReq))
    }

    fun findDto(filter: RecipeFilter, pageable: Pageable): Page<RecipeInfoDto> {
        setCategoryIdFilterWhenFilterNameExist(filter)
        return gateway.findDtoBy(filter, pageable)
    }

    fun setCategoryIdFilterWhenFilterNameExist(filter: RecipeFilter) {
        if(filter.filterNameList.isEmpty())
            return
        val categoryFilter = FoodCategoryFilter(filterName = filter.filterNameList[0])
        foodCategoryService.findAll(categoryFilter, Pageable.unpaged()).get().map(FoodCategory::id).collect(Collectors.toList()).hasValueLet {
            filter.categoryIdList = it
        }
    }

    @Async
    fun addCommentCount(id: Long, count: Int) {
        gateway.addCommentCount(id, count)
    }
}
