package app.foodin.core.gateway

import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerFilter
import app.foodin.domain.banner.BannerType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BannerGateway : BaseGateway<Banner, BannerFilter> {
    fun findByBannerType(bannerType: BannerType, pageable: Pageable): Page<Banner>
}
