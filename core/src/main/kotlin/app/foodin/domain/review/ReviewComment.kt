package app.foodin.domain.review

import app.foodin.common.enums.Status
import app.foodin.domain.comment.BaseComment
import app.foodin.domain.comment.CommentReq
import app.foodin.domain.user.User

data class ReviewComment(
    override var id: Long = 0,
    var reviewId: Long
) : BaseComment<Review>(id, reviewId) {

    var review: Review? = null

    var reviewWriteUserId: Long? = null

    var hasLoved: Boolean = false

    constructor(review: Review, writer: User, commentReq: CommentReq) :
            this(reviewId = review.id) {
        setFromRequestDTO(commentReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.review = review
        this.reviewWriteUserId = review.writeUserId
        this.status = Status.APPROVED
    }
}
