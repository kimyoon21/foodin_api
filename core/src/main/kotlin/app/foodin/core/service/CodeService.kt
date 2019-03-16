package app.foodin.core.service

import app.foodin.core.gateway.CodeGateway
import app.foodin.domain.code.Code
import app.foodin.domain.code.CodeFilter
import app.foodin.domain.user.BaseService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CodeService(
    override val gateway: CodeGateway
) : BaseService<Code, CodeFilter>() {

    private val logger = LoggerFactory.getLogger(CodeService::class.java)
}