package app.foodin.core.service

import app.foodin.common.extension.hasValue
import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.core.gateway.FoodGateway
import app.foodin.core.gateway.LoveGateway
import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.common.EntityType
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDto
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FoodService(
    override val gateway: FoodGateway,
    val foodCategoryGateway: FoodCategoryGateway,
    val loveGateway: LoveGateway,
    val reviewGateway: ReviewGateway
) : BaseService<Food, FoodFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(FoodService::class.java)

    @Async
    fun addReviewAndRatingCount(id: Long, hasContents: Boolean) {
        gateway.addRatingCount(id)
        if (hasContents) {
            gateway.addReviewCount(id)
        }
    }

    fun findNameAll(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDto>? {
        return gateway.findNameAll(filter = filter, pageable = pageable)
    }

    fun findByCategoryFilterName(categoryFilterName: String, pageable: Pageable): Page<Food>? {
        var foodCategoryPage = foodCategoryGateway.findByFilterName(categoryFilterName).content
        val categoryIdList: List<Long> = foodCategoryPage.map { e -> e.id }

        return gateway.findAllByFilter(FoodFilter(categoryIdList = categoryIdList), pageable)
    }

    fun checkReviewAndLove(foodList: List<Food>, userId: Long) {
        for (food in foodList) {
            val loveList = loveGateway.findByUserIdAndEntityTypeAndId(userId = userId, type = EntityType.FOOD, entityId = food.id)
            if (loveList.hasValue()) {
                food.hasLoved = true
            }
            val review = reviewGateway.findByWriteUserIdAndFoodId(writeUserId = userId, foodId = food.id)
            if (review != null) {
                food.hasReview = true
            }
        }
    }
}