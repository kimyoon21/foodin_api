package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_FIELD
import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.service.RecipeService
import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostCreateReq
import app.foodin.domain.recipe.PostFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/recipe"])
class RecipeController(val recipeService: RecipeService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: PostFilter): ResponseResult {
        return ResponseResult(recipeService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/dto"])
    fun getAllWithDto(pageable: Pageable, filter: PostFilter): ResponseResult {
        return ResponseResult(recipeService.findDto(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(recipeService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody postCreateReq: PostCreateReq): ResponseTypeResult<Post> {

        val userInfo = getAuthenticatedUserInfo()
        if (userInfo.id != postCreateReq.writeUserId) {
            throw CommonException(msgCode = EX_INVALID_FIELD, msgArgs = *arrayOf("유저"))
        }
        val result = ResponseTypeResult(recipeService.save(postCreateReq))

        return result
    }

    @DeleteMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long): ResponseTypeResult<Boolean> {
        val result = ResponseTypeResult(recipeService.deleteById(id))

        return result
    }
}
