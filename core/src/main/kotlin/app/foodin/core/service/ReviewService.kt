package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.common.extension.hasValueLet
import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.foodCategory.FoodCategoryFilter
import app.foodin.domain.review.*
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors.toList

@Service
@Transactional
class ReviewService(
    override val gateway: ReviewGateway,
    private val foodService: FoodService,
    private val userService: UserService,
    private val foodCategoryService: FoodCategoryService
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
        var updated = false;
        val review = if (oldReview == null) {
            // 푸드 주입
            val food = foodService.findById(createReq.foodId)

            val writer = userService.findById(createReq.writeUserId)

            Review(food, writer, reviewReq = createReq.reviewReq)
        } else {
            oldReview.setFromRequest(createReq.reviewReq)
            updated = true
            oldReview
        }

        val savedReview = saveFrom(review)
        savedReview.updated = updated
        return savedReview
    }

    @Async
    fun addCommentCount(id: Long, count: Int) {
        gateway.addCommentCount(id, count)
    }

    override fun findAll(filter: ReviewFilter, pageable: Pageable): Page<Review> {
        setCategoryIdFilterWhenFilterNameExist(filter)
        return gateway.findAllByFilter(filter, pageable)
    }

    fun findDto(filter: ReviewFilter, pageable: Pageable): Page<ReviewInfoDto> {
        setCategoryIdFilterWhenFilterNameExist(filter)
        return gateway.findDtoBy(filter, pageable)
    }

    fun setCategoryIdFilterWhenFilterNameExist(filter: ReviewFilter) {
        if(filter.filterNameList.isEmpty())
            return
        val categoryFilter = FoodCategoryFilter(filterName = filter.filterNameList[0])
        foodCategoryService.findAll(categoryFilter, Pageable.unpaged()).get().map(FoodCategory::id).collect(toList()).hasValueLet {
            filter.categoryIdList = it
        }
    }
}
