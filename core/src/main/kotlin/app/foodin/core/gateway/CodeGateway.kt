package app.foodin.core.gateway

import app.foodin.domain.code.Code
import app.foodin.domain.code.CodeFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CodeGateway : BaseGateway<Code, CodeFilter> {
    fun findByCodeType(codeType: String, pageable: Pageable): Page<Code>
}
