package app.foodin.entity.banner

import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerType
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "banner")
data class BannerEntity(
    var imageUri: String,
    var bannerType: BannerType

) : BaseEntity<Banner>() {
    var seq: Int = Integer.MAX_VALUE

    constructor(banner: Banner) : this(banner.imageUri, banner.bannerType) {
        this.seq = banner.seq
    }

    override fun toDomain(): Banner {
        return Banner(imageUri = this.imageUri,
                bannerType = this.bannerType).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.seq = this.seq
        }
    }
}
