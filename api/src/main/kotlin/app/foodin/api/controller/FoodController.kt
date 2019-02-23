package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.food.Food
import app.foodin.domain.user.FoodService
import app.foodin.entity.common.search.SearchSpec
import app.foodin.entity.food.FoodEntity
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/food")
class FoodController(
        private val foodService: FoodService
) {

    @GetMapping
    fun getAll(pageable: Pageable,
               searchSpec: SearchSpec<FoodEntity>): ResponseResult {


        return ResponseResult(foodService.findAll(searchSpec.spec,pageable))
    }

    @GetMapping(value = ["/name"])
    fun getName(@RequestParam name : String): ResponseResult {


        return ResponseResult(foodService.findByName(name))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id : Long): ResponseResult {


        return ResponseResult(foodService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        return ResponseResult(foodService.saveFrom(food))
    }

}