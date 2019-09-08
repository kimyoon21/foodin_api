package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.CommentLoveService
import app.foodin.domain.commentLove.CommentLoveFilter
import app.foodin.domain.commentLove.CommentLoveReq
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/comment-love"])
class CommentLoveController(val commentLoveService: CommentLoveService) {
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
                return ResponseResult(commentLoveService.addOrDelete(commentLoveReq))
    }
}
