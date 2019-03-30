package app.foodin.domain.banner

import app.foodin.domain.BaseFilter

data class BannerFilter(
    val bannerType: BannerType? = null
) : BaseFilter()
