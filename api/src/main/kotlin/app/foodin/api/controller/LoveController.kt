package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.LoveService
import app.foodin.domain.foodLove.LoveFilter
import app.foodin.domain.foodLove.LoveReq
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/love"])
class LoveController(val loveService: LoveService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: LoveFilter
               ): ResponseResult {
        return ResponseResult(loveService.findAll(filter, pageable))
    }

    @PostMapping
    fun loveOrNot(@RequestBody loveReq: LoveReq
    ): ResponseResult {
        return ResponseResult(loveService.addOrDelete(loveReq))
    }

}
