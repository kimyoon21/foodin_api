package app.foodin.entity.review.comment

import app.foodin.common.extension.csvToList
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewComment
import app.foodin.entity.comment.BaseCommentEntity
import app.foodin.entity.review.ReviewEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "review_comment")
@AssociationOverride(name = "parent", joinColumns = [JoinColumn(name = "review_id")])
@AttributeOverride(name = "parentId", column = Column(name = "review_id", insertable = false, updatable = false))
data class ReviewCommentEntity(
    val reviewId: Long,
    override var writeUserId: Long
) : BaseCommentEntity<ReviewComment, Review, ReviewEntity>(reviewId, writeUserId) {

    constructor(reviewComment: ReviewComment) : this(reviewComment.reviewId, reviewComment.writeUserId!!) {
        setBaseFields(reviewComment)
        writeUserEntity = UserEntity(reviewComment.writeUser!!)
        status = reviewComment.status
    }

    override fun toDomain(): ReviewComment {
        return ReviewComment(this.id, reviewId).also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime)
            it.contents = this.contents
            it.imageUriList = this.imageUris.csvToList()
            it.writeUser = this.writeUserEntity.toDomain()
            it.writeUserId = this.writeUserId
            it.status = this.status
        }
    }
}
