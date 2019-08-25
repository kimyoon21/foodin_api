package app.foodin.core.service

import app.foodin.core.gateway.SellerGateway
import app.foodin.domain.seller.Seller
import app.foodin.domain.seller.SellerFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SellerService(override val gateway: SellerGateway) : BaseService<Seller, SellerFilter>()
