package app.foodin.core.service

import app.foodin.common.exception.NotExistsException
import app.foodin.core.gateway.FoodGateway
import app.foodin.core.gateway.FoodRegRequestGateway
import app.foodin.domain.food.Food
import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FoodRegRequestService(
    override val gateway: FoodRegRequestGateway,
    val foodGateway: FoodGateway
) :
        BaseService<FoodRegRequest, FoodRegRequestFilter>() {
    fun approveToFood(id: Long): Food? {
        val foodRegRequest = gateway.findById(id) ?: throw NotExistsException("word.foodRegRequest")
        val food = Food(name = foodRegRequest.name, categoryId = foodRegRequest.categoryId)

        return applyFoodRegRequestToFood(foodRegRequest, food)
    }

    fun mergeToFood(id: Long, foodId: Long): Food? {
        val foodRegRequest = gateway.findById(id) ?: throw NotExistsException("word.foodRegRequest")
        val food = foodGateway.findById(foodId) ?: throw NotExistsException("word.food")

        return applyFoodRegRequestToFood(foodRegRequest, food)
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
}
