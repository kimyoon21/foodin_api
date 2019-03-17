package app.foodin.entity.session

import app.foodin.domain.sessionLog.SessionLog
import app.foodin.core.gateway.SessionLogGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface SessionLogRepository : JpaRepository<SessionLogEntity, Long>

@Component
class JpaSessionLogRepository(private val sessionLogRepository: SessionLogRepository) : SessionLogGateway {
    override fun saveFrom(sessionLog: SessionLog) {
        sessionLogRepository.save(SessionLogEntity(sessionLog))
    }
}