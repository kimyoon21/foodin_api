package app.foodin.core.gateway

import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.review.ReviewComment

interface ReviewCommentGateway : BaseGateway<ReviewComment, CommentFilter> {
    fun addLoveCount(id: Long, count: Int)
}
