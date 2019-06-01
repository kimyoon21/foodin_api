package app.foodin.core.gateway

import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface BaseGateway<T : BaseDomain, F : BaseFilter> {
    fun findAll(spec: Specification<*>?, pageable: Pageable): Page<T>
    fun findAllByFilter(filter: F, pageable: Pageable): Page<T>
    fun findById(id: Long): T?
    fun saveFrom(t: T): T
    fun deleteById(id: Long): Boolean
}
