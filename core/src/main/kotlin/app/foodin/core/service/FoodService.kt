package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.common.extension.hasValue
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.gateway.*
import app.foodin.domain.common.EntityType
import app.foodin.domain.food.*
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class FoodService(
    override val gateway: FoodGateway,
    val foodCategoryGateway: FoodCategoryGateway,
    val loveGateway: LoveGateway,
    val reviewGateway: ReviewGateway,
    val foodFoundUserGateway: FoodFoundUserGateway,
    val sellerGateway: SellerGateway,
    val userGateway: UserGateway

) : StatusService<Food, FoodFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(FoodService::class.java)

    fun addReviewAndRatingInfo(id: Long, hasContents: Boolean, count: Int) {
        gateway.addRatingCount(id, count)
        if (hasContents) {
            gateway.addReviewCount(id, count)
        }
    }

    fun updateFoodRatingAvg(id: Long) {
        gateway.updateRatingAvg(id)
    }

    fun findDto(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDto>? {
        return gateway.findDtoBy(filter = filter, pageable = pageable)
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

    fun findFoodFoundUsers(foodId: Long): List<UserInfoDto> {
        return foodFoundUserGateway.findAllByFoodId(foodId).stream().map { x -> UserInfoDto(x.user) }.distinct().collect(Collectors.toList())
    }

    fun saveFoundUser(foodFoundUserReq: FoodFoundUserReq): FoodFoundUser {

        val food = findById(foodFoundUserReq.foodId)
        foodFoundUserReq.categoryId?.let {
            food.category = foodCategoryGateway.findById(it)
        }
        val seller = sellerGateway.findById(foodFoundUserReq.sellerId)
                ?: throw CommonException(EX_NOT_EXISTS, "word.seller")
        val user = userGateway.findById(getAuthenticatedUserInfo().id)
        return foodFoundUserGateway.saveFrom(FoodFoundUser(food = food, seller = seller, user = user!!))
    }

    fun update(id: Long, req: Food): Food {
        val food = gateway.findById(id) ?: throw CommonException(EX_NOT_EXISTS, "word.entity")
        food.setFromRequest(req)
        food.category = food.categoryId.let { foodCategoryGateway.findById(it) }
        food.writeUser = food.writeUserId?.let { userGateway.findById(it) }
        return saveFrom(food)
    }
}
