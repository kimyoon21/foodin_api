package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.user.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/review")
class ReviewController(
    private val reviewService: ReviewService
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
    fun register(@RequestBody reviewCreateReq: ReviewCreateReq): ResponseResult {

        return ResponseResult(reviewService.saveFrom(Review(reviewCreateReq)))
    }
}