package app.foodin.domain.badge

import app.foodin.domain.common.BaseDomain
import javax.persistence.Entity

@Entity
data class Badge(
        override var id: Long = 0,
        var name: String,
        var color: String
) : BaseDomain()
