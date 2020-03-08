package app.foodin.core.gateway

import app.foodin.domain.sessionLog.SessionLog

interface SessionLogGateway {
    fun saveFrom(sessionLog: SessionLog)
}
