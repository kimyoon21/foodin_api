package app.foodin.entity.banner

import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerType
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.common.StatusEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "banners")
data class BannerEntity(
    var imageUri: String,
    @Enumerated(value = EnumType.STRING)
    var bannerType: BannerType,
    var actionUri: String

) : StatusEntity<Banner>() {
    var seq: Int = Integer.MAX_VALUE

    constructor(banner: Banner) : this(banner.imageUri, banner.bannerType, banner.actionUri) {
        setBaseFieldsFromDomain(banner)
        this.seq = banner.seq
        this.status = banner.status
    }

    override fun toDomain(): Banner {
        return Banner(imageUri = this.imageUri,
                bannerType = this.bannerType,
                actionUri = this.actionUri).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.seq = this.seq
            it.status = this.status
        }
    }
}
