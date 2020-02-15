package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.FoodService
import app.foodin.core.service.RecipeService
import app.foodin.core.service.ReviewService
import app.foodin.core.service.UserService
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.review.ReviewFilter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/****
 * 메인에 노출될 콘텐츠들
 */
@RestController
@RequestMapping("/main")
class MainController(
    private val userService: UserService,
    private val foodService: FoodService,
    private val reviewService: ReviewService,
    private val recipeService: RecipeService
) {

    @GetMapping
    fun getAll(): ResponseResult {
        var result = ResponseResult()

        val userFoodCategoryIdList = getUserFoodCategoryIdList()

        val resultData = mutableMapOf<String, ResponseResult>()
        resultData["food/all"] = getAllFoodOrderByRating(PageRequest.of(0, 10))
        resultData["food/reviewCount"] = getFoodOrderByReviewCount(PageRequest.of(0, 10), userFoodCategoryIdList)
        resultData["food/loveCount"] = getFoodOrderByLoveCount(PageRequest.of(0, 10), userFoodCategoryIdList)
        resultData["review/loveCount"] = getReviewOfMyTaste(PageRequest.of(0, 10), userFoodCategoryIdList)

        result.data = resultData
        result.succeeded = true
        return result
    }

    fun getUserFoodCategoryIdList(): List<Long> {
        val user = userService.findById(getAuthenticatedUserInfo().id)
        return user.userFoodCategoryList.map { it.id }.toList()
    }

    @GetMapping(value = ["/food/all"])
    fun getAllFoodOrderByRating(pageable: Pageable): ResponseResult {
        val pr = PageRequest.of(pageable.pageNumber, 10, Sort.by("ratingAvg").descending())
        return ResponseResult(foodService.findAll(FoodFilter(), pr)).also { it.message = "평점이 훌륭한 푸드" }
    }
    @GetMapping(value = ["/food/reviewCount"])
    fun getFoodOrderByReviewCount(
        pageable: Pageable,
        @RequestParam(required = false) foodCategoryIdList: List<Long>?
    ): ResponseResult {
        val pr = PageRequest.of(pageable.pageNumber, 10, Sort.by("reviewCount").descending())
        val foodFilter = FoodFilter(categoryIdList = foodCategoryIdList ?: getUserFoodCategoryIdList())
        return ResponseResult(foodService.findAll(foodFilter, pr)).also { it.message = "사람들이 많이 먹는 푸드" }
    }
    @GetMapping(value = ["/food/loveCount"])
    fun getFoodOrderByLoveCount(
        pageable: Pageable,
        @RequestParam(required = false) foodCategoryIdList: List<Long>?
    ): ResponseResult {
        val pr = PageRequest.of(pageable.pageNumber, 10, Sort.by("loveCount").descending())
        val foodFilter = FoodFilter(categoryIdList = foodCategoryIdList ?: getUserFoodCategoryIdList())
        return ResponseResult(foodService.findAll(foodFilter, pr)).also { it.message = "모두가 기대하는 푸드" }
    }

    @GetMapping(value = ["/review/loveCount"])
    fun getReviewOfMyTaste(
        pageable: Pageable,
        @RequestParam(required = false) foodCategoryIdList: List<Long>?
    ): ResponseResult {
        val pr = PageRequest.of(pageable.pageNumber, 10, Sort.by("loveCount").descending())
        val reviewFilter = ReviewFilter(categoryIdList = foodCategoryIdList ?: getUserFoodCategoryIdList())
        return ResponseResult(reviewService.findAll(reviewFilter, pr)).also { it.message = "당신이 궁금해할 리뷰" }
    }
}
