package app.foodin.domain.commentLove

import app.foodin.domain.BaseFilter

data class CommentLoveFilter(
    val reviewCommentId: Long? = null,
    val recipeCommentId: Long? = null,
    val userId: Long? = null
) : BaseFilter() {

    constructor(commentLoveReq: CommentLoveReq) : this(
            reviewCommentId = commentLoveReq.reviewCommentId,
            recipeCommentId = commentLoveReq.recipeCommentId,
            userId = commentLoveReq.userId
    )
}
