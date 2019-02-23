package app.foodin.core.gateway

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface BaseGateway<T> {
    fun findAll(spec: Specification<*>?, pageable: Pageable) : Page<T>
    fun findById(id : Long): T?
    fun saveFrom(t: T): T
}
