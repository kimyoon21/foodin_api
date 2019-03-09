package app.foodin.domain.user

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

open class BaseService<T : BaseDomain, F : BaseFilter>(
    private val gateway: BaseGateway<T, F>
) {
//    fun findAll(spec: Specification<*>?, pageable: Pageable): Page<T> {
//        return gateway.findAll(spec, pageable)
//    }

    fun findAll(filter: F, pageable: Pageable): Page<T> {
        return gateway.findAllByFilter(filter, pageable)
    }

    fun findById(id: Long): T {
        return gateway.findById(id) ?: throw CommonException(EX_NOT_EXISTS)
    }

    fun saveFrom(t: T): T {
        return gateway.saveFrom(t)
    }
}