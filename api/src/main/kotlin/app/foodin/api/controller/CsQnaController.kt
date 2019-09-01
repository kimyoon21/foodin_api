package app.foodin.api.controller

import app.foodin.common.extension.onlyAdmin
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.CsQnaService
import app.foodin.domain.csQna.CsQnaCreateReq
import app.foodin.domain.csQna.CsQnaFilter
import app.foodin.domain.csQna.CsQnaUpdateReq
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/qna"])
class CsQnaController(val csQnaService: CsQnaService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: CsQnaFilter): ResponseResult {
        return ResponseResult(csQnaService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
        return ResponseResult(csQnaService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun create(@RequestBody csQna: CsQnaCreateReq): ResponseResult {
        return ResponseResult(csQnaService.create(csQna))
    }

    @PreAuthorize(onlyAdmin)
    @PutMapping(value = ["/{id}"], consumes = ["application/json"])
    fun update(@PathVariable id: Long, @RequestBody csQna: CsQnaUpdateReq): ResponseResult {
        return ResponseResult(csQnaService.update(id, csQna))
    }

    @DeleteMapping(value = ["/{id}"], consumes = ["application/json"])
    fun delete(@PathVariable id: Long): ResponseResult {
        return ResponseResult(csQnaService.delete(id))
    }
}
