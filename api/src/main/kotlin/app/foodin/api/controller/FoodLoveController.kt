package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.FoodLoveService
import app.foodin.domain.foodLove.FoodLoveFilter
import kotlin.Long
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/foodLove")
class FoodLoveController(val foodLoveService: FoodLoveService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: FoodLoveFilter): ResponseResult {
                return ResponseResult(foodLoveService.findAll(filter, pageable))
    }

    @GetMapping
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(foodLoveService.findById(id))
    }
}
