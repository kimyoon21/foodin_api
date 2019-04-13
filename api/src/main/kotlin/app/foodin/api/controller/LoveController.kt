package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.LoveService
import app.foodin.domain.foodLove.LoveFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/love"])
class LoveController(val loveService: LoveService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: LoveFilter
               ): ResponseResult {
        return ResponseResult(loveService.findAll(filter, pageable))
    }

}
