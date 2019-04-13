package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.FoodCategoryService
import app.foodin.domain.foodCategory.FoodCategoryFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/food/category"])
class FoodCategoryController(
    private val foodCategoryService: FoodCategoryService
) {

    @GetMapping
    fun getAll(
        pageable: Pageable,
        filter: FoodCategoryFilter
    ): ResponseResult {

        return ResponseResult(foodCategoryService.findAll(filter, pageable))
    }
}