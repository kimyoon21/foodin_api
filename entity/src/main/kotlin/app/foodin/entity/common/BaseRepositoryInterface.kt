package app.foodin.entity.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepositoryInterface<E> : JpaRepository<E,Long>, JpaSpecificationExecutor<E>
