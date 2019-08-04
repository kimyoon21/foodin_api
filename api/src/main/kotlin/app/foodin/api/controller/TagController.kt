package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.core.service.TagService
import app.foodin.domain.tag.Tag
import app.foodin.domain.tag.TagFilter
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/tag"])
class TagController(val tagService: TagService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: TagFilter): ResponseResult {
                return ResponseResult(tagService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(tagService.findById(id))
    }

    @PostMapping(consumes = ["application/json"])
    fun registerList(@RequestBody tagListReq:TagListReq): ResponseResult {
                return ResponseResult(tagService.saveFrom(tagListReq.tagList))
    }
    data class TagListReq(val tagList: List<Tag>)
}
