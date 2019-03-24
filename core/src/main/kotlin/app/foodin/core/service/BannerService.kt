package app.foodin.core.service

import app.foodin.core.gateway.BannerGateway
import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerFilter
import app.foodin.domain.banner.BannerType
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BannerService(
    override val gateway: BannerGateway
) : BaseService<Banner, BannerFilter>() {

    private val logger = LoggerFactory.getLogger(BannerService::class.java)

    fun findByBannerType(bannerType: BannerType, pageable: Pageable): Page<Banner> {
        return gateway.findByBannerType(bannerType, pageable)
    }
}