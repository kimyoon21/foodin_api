package app.foodin.core.service

import app.foodin.core.gateway.StatusGateway
import app.foodin.domain.StatusFilter
import app.foodin.domain.common.StatusDomain

abstract class StatusService<T : StatusDomain, F : StatusFilter> : BaseService<T, F>() {

    abstract override val gateway: StatusGateway<T, F>

    override fun deleteById(id: Long): Boolean {
        val domain = findById(id)
        return gateway.deleteStatusById(id, domain)
    }
}
