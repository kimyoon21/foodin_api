package app.foodin.entity.csQna

import app.foodin.domain.csQna.CsQna
import app.foodin.domain.csQna.CsQnaFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class CsQnaFilterQuery(val filter: CsQnaFilter) : BaseFilterQuery<CsQna, CsQnaEntity> {
    override fun toSpecification(): Specification<CsQnaEntity>  = filter.let {
        and(
                likeFilter(CsQnaEntity::question,it.query,MatchMode.ANYWHERE),
                equalFilter(CsQnaEntity::writeUserId, it.writerUserId),
                equalUserIdFilterWhenUserPrivateDomain(CsQnaEntity::writeUserId)
        )
    }
}
