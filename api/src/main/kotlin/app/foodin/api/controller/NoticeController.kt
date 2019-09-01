package app.foodin.api.controller

import app.foodin.common.extension.onlyAdmin
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.NoticeService
import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeFilter
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/notice"])
class NoticeController(val noticeService: NoticeService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: NoticeFilter): ResponseResult {
                return ResponseResult(noticeService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(noticeService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody notice: Notice): ResponseResult {
        return ResponseResult(noticeService.saveFrom(notice))
    }

    @PreAuthorize(value = onlyAdmin)
    @PutMapping(value = ["/{id}"], consumes = ["application/json"])
    fun update(@PathVariable id: Long, @RequestBody notice: Notice): ResponseResult {
        return ResponseResult(noticeService.update(id,notice))
    }
}
