package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ALREADY_EXISTS_WHAT
import app.foodin.common.exception.EX_NEED
import app.foodin.core.gateway.PostGateway
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostCreateReq
import app.foodin.domain.recipe.PostFilter
import app.foodin.domain.recipe.PostUserDto
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
    override val gateway: PostGateway,
    val foodService: FoodService,
    val userService: UserService,
    val foodCategoryService: FoodCategoryService
) : StatusService<Post, PostFilter>() {

    fun update(reviewId: Long, req: ReviewReq): Post {
        val review = gateway.findById(reviewId) ?: throw CommonException(EX_ALREADY_EXISTS_WHAT, "word.review")
        return saveFrom(review)
    }

    fun save(createReq: PostCreateReq): Post {
        if (createReq.postReq.foodIdList.isEmpty()) {
            throw CommonException(EX_NEED, "food id")
        }
        val foodList = createReq.postReq.foodIdList.map { foodService.findById(it) }

        val writer = userService.findById(createReq.writeUserId)

        return saveFrom(Post(foodList, writer, recipeReq = createReq.postReq))
    }

    fun findDto(filter: PostFilter, pageable: Pageable): Page<PostUserDto> {
        setCategoryIdFilterWhenFilterNameExist(filter)
        return gateway.findDtoBy(filter, pageable)
    }

    fun setCategoryIdFilterWhenFilterNameExist(filter: PostFilter) {
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
