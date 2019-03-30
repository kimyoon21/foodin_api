package app.foodin.domain.banner

import app.foodin.domain.common.BaseDomain

data class Banner(
    override var id: Long = 0,
    var imageUri: String,
    var bannerType: BannerType

) : BaseDomain(id) {
    var seq: Int = Integer.MAX_VALUE
}