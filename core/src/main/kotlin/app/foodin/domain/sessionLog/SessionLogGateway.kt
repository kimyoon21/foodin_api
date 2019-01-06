package app.foodin.domain.user

import app.foodin.entity.session.SessionLogEntity

interface SessionLogGateway {
    fun save(sessionLogEntity: SessionLogEntity)
}