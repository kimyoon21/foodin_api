package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.food.Food
import app.foodin.domain.user.FoodService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/food")
class FoodController(
        private val foodService: FoodService
) {

    @GetMapping
    fun getAll(pageable: Pageable): ResponseResult {

        SecurityContextHolder.getContext().authentication

        return ResponseResult(foodService.findAll(pageable))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        return ResponseResult(foodService.saveFrom(food))
    }

}