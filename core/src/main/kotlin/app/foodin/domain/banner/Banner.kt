package app.foodin.domain.banner

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.domain.common.StatusDomain

data class Banner(
    override var id: Long = 0,
    var imageUri: String,
    var bannerType: BannerType,
    var actionUri: String

) : StatusDomain(id) {
    var seq: Int = Integer.MAX_VALUE

    override fun setFromRequest(requestDto: Any) {
        if (requestDto is Banner) {
            requestDto.let {
                this.imageUri = it.imageUri
                this.bannerType = it.bannerType
                this.actionUri = it.actionUri
                this.status = it.status
                this.seq = it.seq
            }
        } else {
            throw CommonException(EX_INVALID_REQUEST)
        }
    }
}

