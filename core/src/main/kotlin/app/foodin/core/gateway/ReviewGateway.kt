package app.foodin.core.gateway

import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter

interface ReviewGateway : BaseGateway<Review, ReviewFilter> {
    fun findByWriteUserIdAndFoodId(writeUserId: Long, foodId: Long): Review?
    fun getByUserId(userId: Long): List<Review>
    fun addCommentCount(id: Long)
}