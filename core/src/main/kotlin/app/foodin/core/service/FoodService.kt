package app.foodin.core.service

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.core.gateway.FoodGateway
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDTO
import app.foodin.domain.food.FoodRegRequest
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
    override var gateway: FoodGateway,
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

    fun makeNewFoodOrMergeByFoodRegRequest(foodRegRequest: FoodRegRequest) {
        var food = gateway.findByName(foodRegRequest.name)
        if (food == null) {
            food = Food(name = foodRegRequest.name, categoryId = foodRegRequest.categoryId)
        }

        applyFoodRegRequestToFood(foodRegRequest, food)
        // TODO 생성은 ok 기존꺼 수정은 어떻게?
        gateway.saveFrom(food)
    }

    fun applyFoodRegRequestToFood(foodRegRequest: FoodRegRequest, food: Food): Food {
        food.let {

            it.companyId = foodRegRequest.companyId
            if (it.minPrice == null || it.minPrice!! > foodRegRequest.price) {
                it.minPrice = foodRegRequest.price
            }
            if (it.maxPrice == null || it.maxPrice!! < foodRegRequest.price) {
                it.maxPrice = foodRegRequest.price
            }
            if (foodRegRequest.summary?.length ?: 0 > it.summary?.length ?: 0) {
                it.summary = foodRegRequest.summary
            }
            if (it.mainImageUri == null) {
                it.mainImageUri = foodRegRequest.mainPhotoUri
            }
            it.tagList.addAll(foodRegRequest.tagList)
            it.imageUriList.addAll(foodRegRequest.photoList)

            return it
        }
    }

    fun findByCategoryFilterName(categoryFilterName: String, pageable: Pageable): Page<Food>? {
        var foodCategoryPage = foodCategoryGateway.findByFilterName(categoryFilterName).content
        val categoryIdList: List<Long> = foodCategoryPage.map { e -> e.id }

        return gateway.findAllByFilter(FoodFilter(categoryIdList = categoryIdList), pageable)
    }
}