package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_FIELD
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.CommentLoveService
import app.foodin.core.service.RecipeCommentService
import app.foodin.core.service.RecipeService
import app.foodin.domain.recipeComment.CommentCreateReq
import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.recipeComment.CommentUpdateReq
import app.foodin.domain.recipeComment.PostComment
import java.util.stream.Collectors
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recipe/{rid}/comment")
class RecipeCommentController(
    val recipeCommentService: RecipeCommentService,
    val recipeService: RecipeService,
    val commentLoveService: CommentLoveService
) {
    @GetMapping
    fun getAll(
        @PathVariable rid: Long,
        pageable: Pageable,
        filter: PostCommentFilter
    ): ResponseResult {
        filter.parentId = rid
        val result = recipeCommentService.findAll(filter, pageable)
        if (getAuthenticatedUserInfo().hasUserRole()) {
            val idList = result.get().map { it.id }.collect(Collectors.toList())
            val lovedList = commentLoveService.findAllByUserIdAndRecipeCommentIdIn(getAuthenticatedUserInfo().id, idList)
            result.get().forEach { comment ->
                run {
                    if (lovedList.stream().filter { x -> comment.id == x.postComment?.id }.findFirst().isPresent) {
                        comment.hasLoved = true
                    }
                }
            }
        }
        return ResponseResult(result)
    }

    @PutMapping(value = ["/{id}"])
    fun update(
        @PathVariable id: Long,
        @RequestBody updateReq: CommentUpdateReq
    ): ResponseTypeResult<PostComment> {
        return ResponseTypeResult(recipeCommentService.update(id, updateReq))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(
        @PathVariable rid: Long,
        @RequestBody commentCreateReq: CommentCreateReq
    ): ResponseTypeResult<PostComment> {

        commentCreateReq.parentId = rid
        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != commentCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_INVALID_FIELD, msgArgs = *arrayOf("유저"))
        }
        val result = ResponseTypeResult(recipeCommentService.save(commentCreateReq))
        result.data?.let {
            // count 증가 async
            recipeService.addCommentCount(it.recipeId, 1)
        }

        return result
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(
        @PathVariable rid: Long,
        @PathVariable id: Long
    ): ResponseTypeResult<PostComment> {
        val result = ResponseTypeResult(recipeCommentService.deleteById(id))
        result.data?.let {
            // count 증가 async
            if (it) {
                recipeService.addCommentCount(rid, -1)
            }
        }
        return ResponseTypeResult()
    }
}
