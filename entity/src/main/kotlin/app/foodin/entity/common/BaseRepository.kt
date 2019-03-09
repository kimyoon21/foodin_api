package app.foodin.entity.common

import app.foodin.core.gateway.BaseGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

abstract class BaseRepository<D : BaseDomain, E : BaseEntity<D>, F : BaseFilter>(private val baseJpaRepository: BaseRepositoryInterface<E>) : BaseGateway<D, F> {

    override fun findById(id: Long): D? {
        return baseJpaRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun findAll(spec: Specification<*>?, pageable: Pageable): Page<D> {
        var specification: Specification<E>? = null
        @Suppress("UNCHECKED_CAST")
        if (spec != null)
            specification = spec as (Specification<E>)

        return baseJpaRepository.findAll(specification, pageable).toDomainList()
    }

    override fun findAllByFilter(filter: F, pageable: Pageable): Page<D> {
        return baseJpaRepository.findAll(null, pageable).toDomainList()
    }

    fun findAll(filterQuery: BaseFilterQuery<D, E>, pageable: Pageable): Page<D> {
        return baseJpaRepository.findAll(filterQuery.toSpecification(), pageable).toDomainList()
    }
}

fun <D, E : BaseEntity<D>> Page<E>.toDomainList(): Page<D> {
    return this.map { e -> e.toDomain() }
}

fun <D, E : BaseEntity<D>> List<E>.toDomainList(): List<D> {
    return this.map { e -> e.toDomain() }
}
