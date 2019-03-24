package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.common.result.ResponseTypeResult
import app.foodin.core.service.BannerService
import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerFilter
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/banner")
class BannerController(
    private val bannerService: BannerService
) {

    @GetMapping
    fun getAll(
        pageable: Pageable,
        filter: BannerFilter
    ): ResponseResult {

        return ResponseResult(bannerService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {

        return ResponseResult(bannerService.findById(id))
    }

    @PreAuthorize("hasRole(Role)")
    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody banner: Banner): ResponseTypeResult<Banner> {

        return ResponseTypeResult(bannerService.saveFrom(banner))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = ["application/json"])
    fun update(@RequestBody banner: Banner): ResponseTypeResult<Banner> {
        // TODO
        return ResponseTypeResult(bannerService.saveFrom(banner))
    }
}