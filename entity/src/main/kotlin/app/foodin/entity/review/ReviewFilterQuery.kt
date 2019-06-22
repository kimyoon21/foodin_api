package app.foodin.entity.review

import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class ReviewFilterQuery(
    val filter: ReviewFilter
) : BaseFilterQuery<Review, ReviewEntity> {

    override fun toSpecification(): Specification<ReviewEntity> = filter.let {
        and(
                hasContentsLike(it.name),
                hasTagLike(it.tag),
                querysToSpecification(
                        hasContentsLike(it.query),
                        hasTagLike(it.query)
                ),
                equalFilter(ReviewEntity::foodId, it.foodId)
        )
    }

    companion object {

        fun hasContentsLike(contents: String?): Specification<ReviewEntity>? = likeFilter(ReviewEntity::contents, contents, MathMode.ANYWHERE)

        fun hasTagLike(tag: String?): Specification<ReviewEntity>? = likeFilter(ReviewEntity::tags, tag, MathMode.ANYWHERE)
    }
}
