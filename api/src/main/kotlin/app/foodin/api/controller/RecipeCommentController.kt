package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.RecipeCommentService
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.domain.recipeComment.RecipeCommentFilter
import kotlin.Long
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/recipeComment"])
class RecipeCommentController(val recipeCommentService: RecipeCommentService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: RecipeCommentFilter): ResponseResult {
                return ResponseResult(recipeCommentService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(recipeCommentService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun create(@RequestBody recipeComment: RecipeComment): ResponseResult {
                return ResponseResult(recipeCommentService.saveFrom(recipeComment))
    }

    @DeleteMapping(
            value = ["/{id}"],
            consumes = ["application/json"]
    )
    fun delete(@PathVariable id: Long): ResponseResult {
                return ResponseResult(recipeCommentService.deleteById(id))
    }
}
