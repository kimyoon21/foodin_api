package app.foodin.core.service

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.core.gateway.FoodGateway
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDTO
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
    val foodCategoryGateway: FoodCategoryGateway
) : BaseService<Food, FoodFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(FoodService::class.java)

    @Async
    fun addReviewAndRatingCount(id: Long, hasContents: Boolean) {
        gateway.addRatingCount(id)
        if (hasContents) {
            gateway.addReviewCount(id)
        }
    }

    fun findNameAll(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDTO>? {
        return gateway.findNameAll(filter = filter, pageable = pageable)
    }

    fun findByCategoryFilterName(categoryFilterName: String, pageable: Pageable): Page<Food>? {
        var foodCategoryPage = foodCategoryGateway.findByFilterName(categoryFilterName).content
        val categoryIdList: List<Long> = foodCategoryPage.map { e -> e.id }

        return gateway.findAllByFilter(FoodFilter(categoryIdList = categoryIdList), pageable)
    }
}