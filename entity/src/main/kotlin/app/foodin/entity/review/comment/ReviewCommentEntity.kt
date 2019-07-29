package app.foodin.entity.review.comment

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewComment
import app.foodin.entity.comment.BaseCommentEntity
import app.foodin.entity.review.ReviewEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "review_comment")
@AttributeOverride(name = "parentId", column = Column(name = "review_id"))
@AssociationOverride(name = "parent", joinColumns = [JoinColumn(name = "review_id", insertable = false, updatable = false)])
data class ReviewCommentEntity(
    override var parentId: Long,
    override var writeUserId: Long
) : BaseCommentEntity<ReviewComment, Review, ReviewEntity>(parentId, writeUserId) {

    constructor(reviewComment: ReviewComment) : this(reviewComment.reviewId, reviewComment.writeUserId!!) {
        setBaseFields(reviewComment)
        writeUserEntity = UserEntity(reviewComment.writeUser!!)
        contents = reviewComment.contents
        imageUris = reviewComment.imageUriList.listToCsv()
        status = reviewComment.status
    }

    override fun toDomain(): ReviewComment {
        return ReviewComment(this.id, parentId).also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime)
            it.contents = this.contents
            it.imageUriList = this.imageUris.csvToList()
            it.writeUser = this.writeUserEntity.toDomain()
            it.writeUserId = this.writeUserId
            it.status = this.status
        }
    }
}
