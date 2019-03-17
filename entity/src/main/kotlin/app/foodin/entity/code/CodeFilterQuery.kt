package app.foodin.entity.code

import app.foodin.domain.code.Code
import app.foodin.domain.code.CodeFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

data class CodeFilterQuery(
    val filter: CodeFilter
) : BaseFilterQuery<Code, CodeEntity> {

    override fun toSpecification(): Specification<CodeEntity> = filter.let { and(
            equalFilter(CodeEntity::code, it.code),
            equalFilter(CodeEntity::codeType, it.code),
            likeFilter(CodeEntity::codeName, it.codeName, MathMode.ANYWHERE)
    ) }
}
