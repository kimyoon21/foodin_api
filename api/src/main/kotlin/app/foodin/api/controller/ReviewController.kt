package app.foodin.api.controller

import app.foodin.auth.getAuthenticatedUserInfo
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_FIELD
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.core.service.FoodService
import app.foodin.core.service.ReviewService
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.review.ReviewFilter
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

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {

        return ResponseResult(reviewService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody reviewCreateReq: ReviewCreateReq): ResponseTypeResult<Review> {

        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != reviewCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_INVALID_FIELD, msgArgs = *arrayOf("유저"))
        }
        val result = ResponseTypeResult(reviewService.saveFrom(reviewCreateReq))
        result.data?.let {
            // count 증가 async
            foodService.addReviewAndRatingCount(it.foodId, !it.contents.isNullOrBlank())
        }

        return result
    }
}