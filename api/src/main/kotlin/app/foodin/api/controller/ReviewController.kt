package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ACCESS_DENIED
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.FoodService
import app.foodin.core.service.ReviewService
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.review.ReviewUpdateReq
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/review")
class ReviewController(
    private val reviewService: ReviewService,
    private val foodService: FoodService
) {

    @GetMapping
    fun getAll(
        pageable: Pageable,
        filter: ReviewFilter
    ): ResponseResult {

        return ResponseResult(reviewService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/dto"])
    fun getAllWithDto(
        pageable: Pageable,
        filter: ReviewFilter
    ): ResponseResult {

        return ResponseResult(reviewService.findDto(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {

        return ResponseResult(reviewService.findById(id))
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long, @RequestBody reviewUpdateReq: ReviewUpdateReq): ResponseTypeResult<Review> {
        return ResponseTypeResult(reviewService.update(id, reviewUpdateReq))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody reviewCreateReq: ReviewCreateReq): ResponseTypeResult<Review> {

        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != reviewCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_ACCESS_DENIED)
        }
        val result = ResponseTypeResult(reviewService.save(reviewCreateReq))
        result.data?.also {
            foodService.updateFoodRatingAvg(it.foodId)
            if (!it.updated) {
                // count 증가 async
                foodService.addReviewAndRatingInfo(it.foodId, !it.contents.isNullOrBlank(), 1)
            }
        }

        return result
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseTypeResult<Boolean> {
        val review = reviewService.findById(id)
        val result = ResponseTypeResult(reviewService.deleteById(id))
        result.data?.let {
            // count 증가 async
            foodService.addReviewAndRatingInfo(review.foodId, !review.contents.isNullOrBlank(), -1)
            foodService.updateFoodRatingAvg(review.foodId)
        }
        return result
    }
}
