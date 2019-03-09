package app.foodin.entity.seller

import app.foodin.core.gateway.SellerGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.seller.Seller
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface SellerRepository : BaseRepositoryInterface<SellerEntity> {
    fun findByName(filterName: String): SellerEntity?
}

@Component
class JpaSellerRepository(private val sellerRepository: SellerRepository)
    : BaseRepository<Seller, SellerEntity, BaseFilter>(sellerRepository), SellerGateway {

    override fun findByName(filterName: String): Seller? {
        return sellerRepository.findByName(filterName)?.toDomain()
    }

    override fun saveFrom(t: Seller): Seller {
        return sellerRepository.saveAndFlush(SellerEntity(t)).toDomain()
    }
}