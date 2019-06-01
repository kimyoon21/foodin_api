package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.RecipeService
import app.foodin.domain.recipe.RecipeFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/recipe"])
class RecipeController(val recipeService: RecipeService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: RecipeFilter): ResponseResult {
                return ResponseResult(recipeService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(recipeService.findById(id))
    }
}
