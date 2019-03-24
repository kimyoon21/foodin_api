package app.foodin.entity.banner

import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.and
import app.foodin.entity.common.equalFilter
import org.springframework.data.jpa.domain.Specification

data class BannerFilterQuery(
    val filter: BannerFilter
) : BaseFilterQuery<Banner, BannerEntity> {

    override fun toSpecification(): Specification<BannerEntity> = filter.let { and(
            equalFilter(BannerEntity::bannerType, it.bannerType)
    ) }
}
