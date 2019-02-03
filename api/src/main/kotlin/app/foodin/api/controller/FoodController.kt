package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.food.Food
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/food")
class FoodController {

    @GetMapping
    fun getAll(): ResponseResult {

        SecurityContextHolder.getContext().authentication

        return ResponseResult(Food("음식1",0))
    }

    @PostMapping
    fun register(food: Food): ResponseResult {
        return ResponseResult(Food("음식1",0))
    }

}