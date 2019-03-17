package app.foodin.domain.user

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ALREADY_EXISTS_WHAT
import app.foodin.core.gateway.ReviewGateway
import app.foodin.core.service.UserService
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.review.ReviewFilter
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewService(
    override val gateway: ReviewGateway,
    private val foodService: FoodService,
    private val userService: UserService
) : BaseService<Review, ReviewFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(ReviewService::class.java)

    fun saveFrom(createReq: ReviewCreateReq): Review {
        // 기 데이터 확인
        // 이미 작성한 리뷰(평점)이 있으면 무조건 update
        gateway.findByWriteUserIdAndFoodId(createReq.writeUserId, createReq.foodId)?.let {
            throw CommonException(EX_ALREADY_EXISTS_WHAT, "word.review")
        }

        // 푸드 주입
        val food = foodService.findById(createReq.foodId)
        createReq.food = food

        val writeUser = userService.findById(createReq.writeUserId)
        createReq.writeUser = writeUser

        val review = Review(reviewCreateReq = createReq)

        return saveFrom(review)
    }
}