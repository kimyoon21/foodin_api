package app.foodin.api.controller

import app.foodin.common.extension.onlyAdmin
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.FoodRegRequestService
import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/food/request"])
class FoodRegRequestController(val foodRegRequestService: FoodRegRequestService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: FoodRegRequestFilter): ResponseResult {
                return ResponseResult(foodRegRequestService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(foodRegRequestService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody foodRegRequest: FoodRegRequest): ResponseResult {
        return ResponseResult(foodRegRequestService.saveFrom(foodRegRequest))
    }

    @PreAuthorize(onlyAdmin)
    @PutMapping(value = ["/{id}"], consumes = ["application/json"])
    fun approveToFood(@PathVariable id: Long): ResponseResult {
        return ResponseResult(foodRegRequestService.approveToFood(id))
    }

    @PreAuthorize(onlyAdmin)
    @PutMapping(value = ["/{id}/{foodId}"], consumes = ["application/json"])
    fun mergeToFood(
        @PathVariable id: Long,
        @PathVariable foodId: Long
    ): ResponseResult {
        return ResponseResult(foodRegRequestService.mergeToFood(id, foodId))
    }
}
