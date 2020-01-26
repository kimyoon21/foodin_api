package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_FIELD
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.RecipeService
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeCreateReq
import app.foodin.domain.recipe.RecipeFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

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

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody recipeCreateReq: RecipeCreateReq): ResponseTypeResult<Recipe> {

        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != recipeCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_INVALID_FIELD, msgArgs = *arrayOf("유저"))
        }
        val result = ResponseTypeResult(recipeService.save(recipeCreateReq))

        return result
    }

    @DeleteMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long): ResponseTypeResult<Boolean> {
        val result = ResponseTypeResult(recipeService.deleteById(id))

        return result
    }
}
