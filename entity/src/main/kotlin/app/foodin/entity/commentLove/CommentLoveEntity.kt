package app.foodin.entity.commentLove

import app.foodin.domain.commentLove.CommentLove
import app.foodin.domain.recipe.Recipe
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.recipeComment.RecipeCommentEntity
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
    @JoinColumn(name = "recipe_comment_id")
    var recipeComment: RecipeCommentEntity?,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity
) : BaseEntity<CommentLove>() {

    constructor(commentLove: CommentLove) : this(
            reviewComment = commentLove.reviewComment?.let{ReviewCommentEntity(it)},
            recipeComment = commentLove.recipeComment?.let{RecipeCommentEntity(it)},
            user = UserEntity(commentLove.user)) {
        setBaseFieldsFromDomain(commentLove)
    }

    override fun toDomain(): CommentLove = CommentLove(id,
            reviewComment = reviewComment?.toDomain(),
            recipeComment = recipeComment?.toDomain(),
            user = user.toDomain()).also {
        setDomainBaseFieldsFromEntity(it)
    }
}
