package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.exception.NotExistsException
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.gateway.CommentLoveGateway
import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.core.gateway.ReviewCommentGateway
import app.foodin.domain.commentLove.CommentLove
import app.foodin.domain.commentLove.CommentLoveFilter
import app.foodin.domain.commentLove.CommentLoveReq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentLoveService(
    override val gateway: CommentLoveGateway,
    val reviewCommentGateway: ReviewCommentGateway,
    val recipeCommentGateway: RecipeCommentGateway
) : BaseService<CommentLove,
        CommentLoveFilter>() {
    /***
     * 좋아요 부재시 추가, 존재시 삭제
     */
    fun addOrDelete(commentLoveReq: CommentLoveReq): CommentLove? {
        if (commentLoveReq.reviewCommentId == null && commentLoveReq.recipeCommentId == null) {
            throw CommonException(EX_NEED, "word.targetId")
        }
        if (commentLoveReq.reviewCommentId != null) {
            val oldCommentLove = gateway.findByReviewCommentIdAndUserId(commentLoveReq.reviewCommentId, getAuthenticatedUserInfo().id)
            return if (oldCommentLove == null) {
                val reviewComment = reviewCommentGateway.findById(commentLoveReq.reviewCommentId)
                        ?: throw NotExistsException("word.reviewComment")
                val commentLove = CommentLove(reviewComment = reviewComment, user = getAuthenticatedUserInfo().toUser())
                saveFrom(commentLove)
            } else {
                deleteById(oldCommentLove.id)
                null
            }
        } else if(commentLoveReq.recipeCommentId != null){
            val oldCommentLove = gateway.findByRecipeCommentIdAndUserId(commentLoveReq.recipeCommentId, getAuthenticatedUserInfo().id)
            return if (oldCommentLove == null) {
                val recipeComment = recipeCommentGateway.findById(commentLoveReq.recipeCommentId)
                        ?: throw NotExistsException("word.reviewComment")
                val commentLove = CommentLove(postComment = recipeComment, user = getAuthenticatedUserInfo().toUser())
                saveFrom(commentLove)
            } else {
                deleteById(oldCommentLove.id)
                null
            }
        }else{
            throw Exception()
        }
    }

    fun findAllByUserIdAndReviewCommentIdIn(userId: Long, reviewCommentIds: MutableList<Long>): List<CommentLove> {
        return gateway.findAllByUserIdAndReviewCommentIdIn(userId, reviewCommentIds)
    }

    fun findAllByUserIdAndRecipeCommentIdIn(userId: Long, recipeCommentIds: MutableList<Long>): List<CommentLove> {
        return gateway.findAllByUserIdAndRecipeCommentIdIn(userId, recipeCommentIds)
    }
}
