package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.review.*
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewService(
    override val gateway: ReviewGateway,
    private val foodService: FoodService,
    private val userService: UserService
) : StatusService<Review, ReviewFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(ReviewService::class.java)

    fun update(reviewId: Long, req: ReviewReq): Review {
        val review = gateway.findById(reviewId) ?: throw CommonException(EX_NOT_EXISTS, "word.review")
        review.setFromRequest(req)
        return saveFrom(review)
    }

    fun save(createReq: ReviewCreateReq): Review {
        // 기 데이터 확인
        // 이미 작성한 리뷰(평점)이 있으면 무조건 update
        val oldReview = gateway.findByWriteUserIdAndFoodId(createReq.writeUserId, createReq.foodId)
        val review = if (oldReview == null) {
            // 푸드 주입
            val food = foodService.findById(createReq.foodId)

            val writer = userService.findById(createReq.writeUserId)

            Review(food, writer, reviewReq = createReq.reviewReq)
        } else {
            oldReview.setFromRequest(createReq.reviewReq)
            oldReview
        }

        return saveFrom(review)
    }

    @Async
    fun addCommentCount(id: Long, count: Int) {
        gateway.addCommentCount(id, count)
    }

    fun findDto(filter: ReviewFilter, pageable: Pageable): Page<ReviewInfoDto> {
        return gateway.findDtoBy(filter, pageable)
    }
}
