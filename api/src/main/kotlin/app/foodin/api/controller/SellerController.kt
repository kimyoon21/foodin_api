package app.foodin.api.controller

import app.foodin.common.extension.onlyAdmin
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.SellerService
import app.foodin.domain.seller.Seller
import app.foodin.domain.seller.SellerFilter
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/seller"])
class SellerController(val sellerService: SellerService) {
    @GetMapping
    fun getAll(pageable: Pageable, filter: SellerFilter): ResponseResult {
                return ResponseResult(sellerService.findAll(filter, pageable))
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable id: Long): ResponseResult {
                return ResponseResult(sellerService.findById(id))
    }

    @PreAuthorize(onlyAdmin)
    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody seller: Seller): ResponseResult {
                return ResponseResult(sellerService.saveFrom(seller))
    }

    @PreAuthorize(onlyAdmin)
    @PutMapping(value = ["/{id}"], consumes = ["application/json"])
    fun update(@PathVariable id: Long, @RequestBody seller: Seller): ResponseResult {
        return ResponseResult(sellerService.update(id, seller))
    }

    @PreAuthorize(onlyAdmin)
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseResult {
        return ResponseResult(sellerService.deleteById(id))
    }
}
