package app.foodin.domain.user

import app.foodin.domain.sessionLog.SessionLog

interface SessionLogGateway {
    fun saveFrom(sessionLog: SessionLog)
}