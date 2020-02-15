package app.foodin.entity.common

import app.foodin.common.enums.Status
import app.foodin.core.gateway.StatusGateway
import app.foodin.domain.StatusFilter
import app.foodin.domain.common.StatusDomain

abstract class StatusRepository<D : StatusDomain, E : StatusEntity<D>, F : StatusFilter>(jpaRepository: BaseRepositoryInterface<E>) : StatusGateway<D, F>, BaseRepository<D, E, F>(jpaRepository) {

    override fun deleteStatusById(id: Long, domain:D): Boolean {
        domain.status = Status.DELETED
        saveFrom(domain)
        return true
    }

}
