package app.foodin.domain.banner

import app.foodin.domain.StatusFilter

data class BannerFilter(
    val bannerType: BannerType? = null
) : StatusFilter()
