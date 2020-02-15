package app.foodin.core.gateway

import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.review.ReviewInfoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReviewGateway : StatusGateway<Review, ReviewFilter> {
    fun findByWriteUserIdAndFoodId(writeUserId: Long, foodId: Long): Review?
    fun getByUserId(userId: Long): List<Review>
    fun addCommentCount(id: Long, count: Int)
    fun findDtoBy(filter: ReviewFilter, pageable: Pageable): Page<ReviewInfoDto>
    fun addLoveCount(id: Long, count: Int)
}
