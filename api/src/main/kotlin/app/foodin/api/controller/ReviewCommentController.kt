package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_FIELD
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.ReviewCommentService
import app.foodin.core.service.ReviewService
import app.foodin.domain.comment.CommentCreateReq
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.comment.CommentUpdateReq
import app.foodin.domain.review.ReviewComment
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/review/{rid}/comment")
class ReviewCommentController(
    private val reviewCommentService: ReviewCommentService,
    private val reviewService: ReviewService
) {

    @GetMapping
    fun getAll(
        @PathVariable rid: Long,
        pageable: Pageable,
        filter: CommentFilter
    ): ResponseResult {
        filter.parentId = rid
        return ResponseResult(reviewCommentService.findAll(filter, pageable))
    }

    @PutMapping(value = ["/{id}"])
    fun update(
        @PathVariable id: Long,
        @RequestBody updateReq: CommentUpdateReq
    ): ResponseTypeResult<ReviewComment> {
        return ResponseTypeResult(reviewCommentService.update(id, updateReq))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(
        @PathVariable rid: Long,
        @RequestBody commentCreateReq: CommentCreateReq
    ): ResponseTypeResult<ReviewComment> {

        commentCreateReq.parentId = rid
        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != commentCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_INVALID_FIELD, msgArgs = *arrayOf("유저"))
        }
        val result = ResponseTypeResult(reviewCommentService.save(commentCreateReq))
        result.data?.let {
            // count 증가 async
            reviewService.addCommentCount(it.reviewId)
        }

        return result
    }
}