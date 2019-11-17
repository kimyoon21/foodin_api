package app.foodin.core.gateway

import app.foodin.domain.commentLove.CommentLove
import app.foodin.domain.commentLove.CommentLoveFilter

interface CommentLoveGateway : BaseGateway<CommentLove, CommentLoveFilter> {
    fun findByReviewCommentIdAndUserId(reviewCommentId: Long, userId: Long): CommentLove?
    fun findAllByReviewCommentId(reviewCommentId: Long): List<CommentLove>
    fun findAllByUserIdAndReviewCommentIdIn(userId: Long, reviewCommentIds: MutableList<Long>): List<CommentLove>
}
