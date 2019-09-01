package app.foodin.entity.csQna

import app.foodin.core.gateway.CsQnaGateway
import app.foodin.domain.csQna.CsQna
import app.foodin.domain.csQna.CsQnaFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface CsQnaRepository : BaseRepositoryInterface<CsQnaEntity>

@Component
class JpaCsQnaRepository(private val repository: CsQnaRepository) : BaseRepository<CsQna,
        CsQnaEntity, CsQnaFilter>(repository), CsQnaGateway {
    override fun findAllByFilter(filter: CsQnaFilter, pageable: Pageable): Page<CsQna> {
                return findAll(CsQnaFilterQuery(filter), pageable)
    }

    override fun saveFrom(csQna: CsQna): CsQna {
                return repository.saveAndFlush(CsQnaEntity(csQna)).toDomain()
    }
}
