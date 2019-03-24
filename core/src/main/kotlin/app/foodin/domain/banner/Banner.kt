package app.foodin.domain.banner

import app.foodin.domain.common.BaseDomain
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class Banner(
    override var id: Long = 0,
    @field:NotEmpty(message = "{imageUri.not_empty")
    var imageUri: String,
    @field:NotNull
    var bannerType: BannerType

) : BaseDomain(id) {
    @Max(value = 100)
    var seq: Int = Integer.MAX_VALUE
}