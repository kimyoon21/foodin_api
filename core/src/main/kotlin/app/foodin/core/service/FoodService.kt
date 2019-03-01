package app.foodin.domain.user

import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodRegRequest
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FoodService(
        private val foodGateway: FoodGateway
) : BaseService<Food>(foodGateway), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(FoodService::class.java)

    fun findByName(name: String) : Food? {
        return foodGateway.findByName(name)
    }

    fun makeNewFoodOrMergeByFoodRegRequest(foodRegRequest: FoodRegRequest) {
        var food = foodGateway.findByName(foodRegRequest.name)
        if (food == null) {
            food = Food(name=foodRegRequest.name, categoryId = foodRegRequest.categoryId)
        }

        applyFoodRegRequestToFood(foodRegRequest,food)
        // TODO 생성은 ok 기존꺼 수정은 어떻게?
        foodGateway.saveFrom(food)
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
            if(foodRegRequest.summary?.length ?: 0 > it.summary?.length ?: 0){
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