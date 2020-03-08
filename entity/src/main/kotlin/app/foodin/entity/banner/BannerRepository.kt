package app.foodin.entity.banner

import app.foodin.core.gateway.BannerGateway
import app.foodin.domain.banner.Banner
import app.foodin.domain.banner.BannerFilter
import app.foodin.domain.banner.BannerType
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.toDomainList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository : BaseRepositoryInterface<BannerEntity> {
    fun findByBannerType(bannerType: BannerType, pageable: Pageable): Page<BannerEntity>
}

@Component
class BannerJpaRepository(private val bannerRepository: BannerRepository) :
    BaseRepository<Banner, BannerEntity, BannerFilter>(bannerRepository), BannerGateway {

    override fun findAllByFilter(filter: BannerFilter, pageable: Pageable): Page<Banner> {
        return findAll(BannerFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: Banner): Banner {
        return bannerRepository.saveAndFlush(BannerEntity(t)).toDomain()
    }

    override fun findByBannerType(bannerType: BannerType, pageable: Pageable): Page<Banner> {
        return bannerRepository.findByBannerType(bannerType = bannerType, pageable = pageable).toDomainList()
    }
}
