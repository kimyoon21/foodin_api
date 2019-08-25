package app.foodin.entity.seller

import app.foodin.domain.seller.Seller
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sellers")
data class SellerEntity(
    var name: String
) : BaseEntity<Seller>() {

    var foodCount: Int = 0

    var spotCount: Int = 0

    var nameKr: String? = null

    var profileImageUri: String? = null

    var keywords: String? = null

    constructor(seller: Seller) : this(seller.name)

    override fun toDomain(): Seller {
        return Seller(name = this.name).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.foodCount = this.foodCount
            it.spotCount = this.spotCount
            it.profileImageUri = this.profileImageUri
        }
    }
}
