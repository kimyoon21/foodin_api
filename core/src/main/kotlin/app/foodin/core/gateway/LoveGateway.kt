package app.foodin.core.gateway

import app.foodin.domain.common.EntityType
import app.foodin.domain.love.Love
import app.foodin.domain.love.LoveFilter

interface LoveGateway : BaseGateway<Love, LoveFilter>{
    fun findByUserIdAndEntityTypeAndId(userId: Long, type: EntityType, entityId: Long): List<Love>

}
