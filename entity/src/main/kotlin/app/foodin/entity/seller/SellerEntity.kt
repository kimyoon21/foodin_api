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

    var summary: String? = null

    var iconImageUri: String? = null

    var profileImageUri: String? = null

    constructor(seller: Seller) : this(seller.name) {
        setBaseFieldsFromDomain(seller)
        this.foodCount = seller.foodCount
        this.spotCount = seller.spotCount
        this.summary = seller.summary
        this.iconImageUri = seller.iconImageUri
        this.profileImageUri = seller.profileImageUri
    }

    override fun toDomain(): Seller {
        return Seller(name = this.name).also {
            setDomainBaseFieldsFromEntity(it)
            it.foodCount = this.foodCount
            it.spotCount = this.spotCount
            it.profileImageUri = this.profileImageUri
            it.summary = this.summary
            it.iconImageUri = this.iconImageUri
        }
    }
}
