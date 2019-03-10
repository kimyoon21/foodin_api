package app.foodin.domain.user

import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewGateway: ReviewGateway
) : BaseService<Review, ReviewFilter>(reviewGateway), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(ReviewService::class.java)
}