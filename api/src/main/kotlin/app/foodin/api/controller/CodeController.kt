package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.CodeService
import app.foodin.domain.code.CodeFilter
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/code")
class CodeController(
    private val codeService: CodeService
) {

    @GetMapping
    fun getAll(
        @PageableDefault(page = 0, size = 100) pageable: Pageable,
        codeFilter: CodeFilter
    ): ResponseResult {

        return ResponseResult(codeService.findAll(codeFilter, pageable))
    }
}