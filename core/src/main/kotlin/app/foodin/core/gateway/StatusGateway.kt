package app.foodin.core.gateway

import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain

interface StatusGateway<T : BaseDomain, F : BaseFilter> : BaseGateway<T, F> {
    fun deleteStatusById(id: Long, domain: T): Boolean
}
