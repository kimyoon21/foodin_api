package app.foodin.api.controller

import app.foodin.common.extension.onlyAdmin
import app.foodin.common.extension.onlyUser
import app.foodin.common.result.ResponseResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.FoodCategoryService
import app.foodin.core.service.FoodService
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodFoundUserReq
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/food")
class FoodController(
    private val foodService: FoodService,
    private val foodCategoryService: FoodCategoryService
) {

    @GetMapping
    fun getAll(
        pageable: Pageable,
        filter: FoodFilter
    ): ResponseResult {

        return ResponseResult(foodService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/dto"])
    fun getAllWithDto(
        pageable: Pageable,
        filter: FoodFilter
    ): ResponseResult {

        return ResponseResult(foodService.findDto(filter, pageable))
    }

    @GetMapping(value = ["/categoryFilter"])
    fun getByCategoryFilterName(categoryFilterName: String, pageable: Pageable): ResponseResult {

        return ResponseResult(foodService.findByCategoryFilterName(categoryFilterName, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {

        val food = foodService.findById(id)
        food.foundUserList = foodService.findFoodFoundUsers(id)

        if (getAuthenticatedUserInfo().hasUserRole()) {
            // review 및 love 여부 체크
            foodService.checkReviewAndLove(listOf(food), getAuthenticatedUserInfo().id)
        }

        return ResponseResult(food)
    }

    /*****
     * admin 만 바로 등록이 가능
     * 유저는 foodReqRequest 사용
     */
    @PreAuthorize(onlyAdmin)
    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        food.category = foodCategoryService.findById(food.categoryId)
        return ResponseResult(foodService.saveFrom(food))
    }

    @PreAuthorize(onlyUser)
    @PostMapping(value = ["/{id}/found"], consumes = ["application/json"])
    fun found(
        @RequestBody foodFoundUserReq: FoodFoundUserReq,
        @PathVariable id: Long
    ): ResponseResult {
        foodFoundUserReq.foodId = id
        return ResponseResult(foodService.saveFoundUser(foodFoundUserReq))
    }

    @PreAuthorize(onlyAdmin)
    @PutMapping(value = ["/{id}"], consumes = ["application/json"])
    fun update(
        @RequestBody food: Food,
        @PathVariable id: Long
    ): ResponseResult {
        return ResponseResult(foodService.update(id, food))
    }

    @PreAuthorize(onlyAdmin)
    @DeleteMapping(value = ["/{id}"], consumes = ["application/json"])
    fun delete(
        @PathVariable id: Long
    ): ResponseResult {
        return ResponseResult(foodService.deleteById(id))
    }
}
