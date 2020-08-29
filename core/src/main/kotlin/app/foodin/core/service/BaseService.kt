package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.common.exception.NotExistsException
import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

abstract class BaseService<T : BaseDomain, F : BaseFilter> {

    abstract val gateway: BaseGateway<T, F>

    open fun findAll(filter: F, pageable: Pageable): Page<T> {
        return gateway.findAllByFilter(filter, pageable)
    }

    fun findById(id: Long): T {
        return gateway.findById(id) ?: throw NotExistsException()
    }

    fun saveFrom(t: T): T {
        return gateway.saveFrom(t)
    }

    open fun deleteById(id: Long): Boolean {
        return gateway.deleteById(id)
    }

    fun update(id: Long, req: Any): T {
        val domian = gateway.findById(id) ?: throw CommonException(EX_NOT_EXISTS, "word.entity")
        domian.setFromRequest(req)
        return saveFrom(domian)
    }
}
