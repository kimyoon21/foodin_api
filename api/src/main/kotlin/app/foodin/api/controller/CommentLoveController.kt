package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.CommentLoveService
import app.foodin.core.service.ReviewCommentService
import app.foodin.domain.commentLove.CommentLoveFilter
import app.foodin.domain.commentLove.CommentLoveReq
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/comment-love"])
class CommentLoveController(
    val commentLoveService: CommentLoveService,
    val reviewCommentService: ReviewCommentService
) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: CommentLoveFilter): ResponseResult {
                return ResponseResult(commentLoveService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(commentLoveService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun createOrDelete(@RequestBody commentLoveReq: CommentLoveReq): ResponseResult {

        val commentLove = commentLoveService.addOrDelete(commentLoveReq)
        if (commentLove?.reviewComment != null) {
            reviewCommentService.addLoveCount(commentLoveReq.reviewCommentId!!,1)
        } else{
            reviewCommentService.addLoveCount(commentLoveReq.reviewCommentId!!,-1)
        }
        return ResponseResult(commentLove)
    }
}
