package app.foodin.entity.review

import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.entity.common.*
import app.foodin.entity.food.FoodEntity
import org.springframework.data.jpa.domain.Specification

class ReviewFilterQuery(
    val filter: ReviewFilter
) : BaseFilterQuery<Review, ReviewEntity> {

    override fun toSpecification(): Specification<ReviewEntity> = filter.let { filter ->
        and(
                hasContentsLike(filter.name),
                hasTagLike(filter.tag),
                // 푸드카테고리도 리뷰에 적용
                filterIfHasValue(filter.categoryIdList, where { it.join(ReviewEntity::food).get(FoodEntity::categoryId).`in`(filter.categoryIdList) }),
                querysToSpecification(
                        hasContentsLike(filter.query),
                        hasTagLike(filter.query)
                ),
                isNotNullFilter(ReviewEntity::mainImageUri,filter.hasImage),
                equalFilter(ReviewEntity::foodId, filter.foodId),
                equalFilter(ReviewEntity::writeUserId, filter.writeUserId)

        )
    }

    companion object {

        fun hasContentsLike(contents: String?): Specification<ReviewEntity>? = likeFilter(ReviewEntity::contents, contents, MatchMode.ANYWHERE)

        fun hasTagLike(tag: String?): Specification<ReviewEntity>? = likeFilter(ReviewEntity::tags, tag, MatchMode.ANYWHERE)
    }
}
