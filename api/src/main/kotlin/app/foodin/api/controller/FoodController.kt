package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.user.FoodService
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

        return ResponseResult(foodService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        return ResponseResult(foodService.saveFrom(food))
    }
}