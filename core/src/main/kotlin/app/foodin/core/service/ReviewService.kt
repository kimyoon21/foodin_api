package app.foodin.domain.user

import app.foodin.core.gateway.ReviewGateway
import app.foodin.core.service.UserService
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
// @Transactional
class ReviewService(
    reviewGateway: ReviewGateway,
    private val foodService: FoodService,
    private val userService: UserService
) : BaseService<Review, ReviewFilter>(reviewGateway), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(ReviewService::class.java)

    fun saveFrom(createReq: ReviewCreateReq): Review {

        // 푸드 주입
        val food = foodService.findById(createReq.foodId)
        createReq.food = food

        val writeUser = userService.findById(createReq.writeUserId)
        createReq.writeUser = writeUser

        val review = Review(reviewCreateReq = createReq)

        return saveFrom(review)
    }
}