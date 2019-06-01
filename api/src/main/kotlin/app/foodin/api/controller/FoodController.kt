package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.FoodService
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/food")
class FoodController(
    private val foodService: FoodService
) {

    @GetMapping
    fun getAll(
        pageable: Pageable,
        filter: FoodFilter
    ): ResponseResult {

        return ResponseResult(foodService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/name"])
    fun getName(
        pageable: Pageable,
        filter: FoodFilter
    ): ResponseResult {

        return ResponseResult(foodService.findNameAll(filter, pageable))
    }

    @GetMapping(value = ["/categoryFilter"])
    fun getByCategoryFilterName(categoryFilterName: String, pageable: Pageable): ResponseResult {

        return ResponseResult(foodService.findByCategoryFilterName(categoryFilterName, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {

        val result = ResponseResult(foodService.findById(id))
        if(getAuthenticatedUserInfo().hasUserRole()) {
            // review 및 love 여부 체크
            foodService.checkReviewAndLove(listOf(result.data as Food), getAuthenticatedUserInfo().id)
        }
        return result
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        return ResponseResult(foodService.saveFrom(food))
    }
}