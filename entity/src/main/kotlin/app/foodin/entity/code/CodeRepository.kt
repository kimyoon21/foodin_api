package app.foodin.entity.code

import app.foodin.core.gateway.CodeGateway
import app.foodin.domain.code.Code
import app.foodin.domain.code.CodeFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.toDomainList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface CodeRepository : BaseRepositoryInterface<CodeEntity> {
    fun findByCodeType(codeType: String, pageable: Pageable): Page<CodeEntity>
}

@Component
class CodeJpaRepository(private val codeRepository: CodeRepository) :
    BaseRepository<Code, CodeEntity, CodeFilter>(codeRepository), CodeGateway {
    override fun findAllByFilter(filter: CodeFilter, pageable: Pageable): Page<Code> {
        return findAll(CodeFilterQuery(filter), pageable)
    }

    override fun findByCodeType(codeType: String, pageable: Pageable): Page<Code> {
        return codeRepository.findByCodeType(codeType, pageable).toDomainList()
    }

    override fun saveFrom(t: Code): Code {
        return codeRepository.saveAndFlush(CodeEntity(t)).toDomain()
    }
}
