package app.foodin.core.service

import app.foodin.common.exception.NotExistsException
import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

abstract class BaseService<T : BaseDomain, F : BaseFilter> {
    constructor()

    abstract val gateway: BaseGateway<T, F>

    fun findAll(filter: F, pageable: Pageable): Page<T> {
        return gateway.findAllByFilter(filter, pageable)
    }

    fun findById(id: Long): T {
        return gateway.findById(id) ?: throw NotExistsException()
    }

    fun saveFrom(t: T): T {
        return gateway.saveFrom(t)
    }
}