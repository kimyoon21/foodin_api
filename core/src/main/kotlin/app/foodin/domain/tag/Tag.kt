package app.foodin.domain.tag

import app.foodin.domain.common.BaseDomain

data class Tag(
    override var id: Long = 0L,
    val name: String
) : BaseDomain(id) {
    var useCount = 0
}
