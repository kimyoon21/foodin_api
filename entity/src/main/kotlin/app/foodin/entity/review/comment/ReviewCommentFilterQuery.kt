package app.foodin.entity.review.comment

import app.foodin.common.extension.hasIdLet
import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.review.ReviewComment
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

data class ReviewCommentFilterQuery(
    val filter: PostCommentFilter
) : BaseFilterQuery<ReviewComment, ReviewCommentEntity> {

    override fun toSpecification(): Specification<ReviewCommentEntity> = filter.let {
        and(
            likeFilter(ReviewCommentEntity::contents, it.contents, MatchMode.ANYWHERE),
            it.parentId.hasIdLet {
                x -> equalFilter(ReviewCommentEntity::parentId, x)
            },
            equalFilter(ReviewCommentEntity::writeUserId, it.writeUserId)
    ) }
}
