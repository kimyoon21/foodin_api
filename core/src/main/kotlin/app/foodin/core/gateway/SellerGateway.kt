package app.foodin.core.gateway

import app.foodin.domain.BaseFilter
import app.foodin.domain.seller.Seller

interface SellerGateway : BaseGateway<Seller, BaseFilter> {
    fun findByName(name: String): Seller?
}
