package app.foodin.core.gateway

import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter

interface ReviewGateway : BaseGateway<Review, ReviewFilter> {
    fun findByWriteUserIdAndFoodId(userId: Long, foodId: Long): Review?
}