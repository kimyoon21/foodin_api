package app.foodin.core.gateway

import app.foodin.domain.seller.Seller

interface SellerGateway : BaseGateway<Seller> {
    fun findByName(name: String) : Seller?
}
