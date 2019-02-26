package app.foodin.entity.common

import app.foodin.domain.common.BaseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

abstract class BaseRepository<T:BaseDomain,E:BaseEntity<T>>(private val baseJpaRepository: BaseRepositoryInterface<E>) {

    open fun findById(id: Long): T? {
        return baseJpaRepository.findById(id).orElse(null)?.toDomain()
    }

    open fun findAll(spec: Specification<*>?, pageable: Pageable): Page<T> {
        @Suppress("UNCHECKED_CAST")
        spec as (Specification<E>)

        return baseJpaRepository.findAll(spec, pageable).map { e -> e.toDomain() }
    }

}

