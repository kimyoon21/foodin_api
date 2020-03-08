package app.foodin.entity.seller

import app.foodin.core.gateway.SellerGateway
import app.foodin.domain.seller.Seller
import app.foodin.domain.seller.SellerFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface SellerRepository : BaseRepositoryInterface<SellerEntity> {
    fun findByName(filterName: String): SellerEntity?
}

@Component
class JpaSellerRepository(private val sellerRepository: SellerRepository) :
    BaseRepository<Seller, SellerEntity, SellerFilter>(sellerRepository), SellerGateway {

    override fun findByName(name: String): Seller? {
        return sellerRepository.findByName(name)?.toDomain()
    }

    override fun saveFrom(t: Seller): Seller {
        return sellerRepository.saveAndFlush(SellerEntity(t)).toDomain()
    }
}
