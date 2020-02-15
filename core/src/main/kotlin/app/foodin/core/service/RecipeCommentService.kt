package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.domain.comment.CommentCreateReq
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.comment.CommentUpdateReq
import app.foodin.domain.recipeComment.RecipeComment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeCommentService(override val gateway: RecipeCommentGateway,
                           val recipeService: RecipeService,
                           val userService: UserService) : BaseService<RecipeComment,
        CommentFilter>(){



    fun save(createReq: CommentCreateReq): RecipeComment {
        // 기 데이터 확인
        // 이미 작성한 리뷰(평점)이 있으면 무조건 update
        val recipe = recipeService.findById(createReq.parentId)

        val writer = userService.findById(createReq.writeUserId)

        return saveFrom(RecipeComment(recipe, writer, createReq.commentReq))
    }

    fun update(id: Long, updateReq: CommentUpdateReq): RecipeComment? {
        val recipeComment = gateway.findById(id) ?: throw CommonException(EX_NOT_EXISTS, "댓글")
        recipeComment.setFromRequestDTO(updateReq.commentReq)
        return saveFrom(recipeComment)
    }

    fun addLoveCount(id: Long, count: Int) {
        gateway.addLoveCount(id, count)
    }
}
