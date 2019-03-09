package app.foodin.entity.code

import app.foodin.domain.code.Code
import app.foodin.domain.code.CodeFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.and
import app.foodin.entity.common.equal
import app.foodin.entity.common.like
import org.springframework.data.jpa.domain.Specification

data class CodeFilterQuery(
    val filter: CodeFilter
) : BaseFilterQuery<Code, CodeEntity> {

    override fun toSpecification(): Specification<CodeEntity> = filter.let { and(
            isCode(it.code),
            isCodeType(it.codeType),
            hasCodeName(it.codeName)
    ) }
}

fun isCode(q: String?): Specification<CodeEntity>? = q?.let {
    CodeEntity::code.equal(it)
}
fun isCodeType(q: String?): Specification<CodeEntity>? = q?.let {
    CodeEntity::codeType.equal(it)
}
fun hasCodeName(q: String?): Specification<CodeEntity>? = q?.let {
    CodeEntity::codeName.like("%$q%")
}
