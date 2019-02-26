package app.foodin.entity.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BaseRepositoryInterface<E> : JpaRepository<E,Long>, JpaSpecificationExecutor<E>
