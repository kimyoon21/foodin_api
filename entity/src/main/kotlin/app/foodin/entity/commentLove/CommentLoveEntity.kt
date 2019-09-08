package app.foodin.entity.commentLove

import app.foodin.domain.commentLove.CommentLove
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.review.comment.ReviewCommentEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "comment_love")
data class CommentLoveEntity(

    @ManyToOne
    @JoinColumn(name = "review_comment_id")
    var reviewComment: ReviewCommentEntity?,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity
) : BaseEntity<CommentLove>() {

//    @Column(name = "review_comment_id", insertable = false, updatable = false)
//    val reviewCommentId: Long
//    //        val recipeCommentId : Long?,
//    @Column(name = "user_id", insertable = false, updatable = false)
//    val userId: Long

    constructor(commentLove: CommentLove) : this(reviewComment = ReviewCommentEntity(commentLove.reviewComment!!),
//            recipeCommentId = commentLove.recipeCommentId,
            user = UserEntity(commentLove.user)) {
        setBaseFieldsFromDomain(commentLove)
    }

    override fun toDomain(): CommentLove = CommentLove(id, reviewComment?.toDomain(), user.toDomain()).also {
        setDomainBaseFieldsFromEntity(it)
    }
}
