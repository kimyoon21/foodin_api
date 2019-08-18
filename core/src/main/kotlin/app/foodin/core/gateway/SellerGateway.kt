package app.foodin.core.gateway

import app.foodin.domain.seller.Seller
import app.foodin.domain.seller.SellerFilter

interface SellerGateway : BaseGateway<Seller, SellerFilter> {
    fun findByName(name: String): Seller?
}
