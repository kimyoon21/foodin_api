package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.user.FoodCategoryService
import app.foodin.entity.common.search.SearchSpec
import app.foodin.entity.foodCategory.FoodCategoryEntity
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/food/category")
class FoodCategoryController(
        private val foodCategoryService: FoodCategoryService
) {

    @GetMapping
    fun getAll(pageable: Pageable,
               searchSpec: SearchSpec<FoodCategoryEntity>?): ResponseResult {


        return ResponseResult(foodCategoryService.findAll(searchSpec?.spec,pageable))
    }

}