package app.foodin.entity.session

import app.foodin.domain.sessionLog.SessionLog
import java.sql.Timestamp
import javax.persistence.*
import org.hibernate.annotations.CreationTimestamp

@Entity
@Table(name = "session_logs")
data class SessionLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @CreationTimestamp
    val createTime: Timestamp? = null,
    val userId: Long,
    @Column(length = 600)
    val token: String

) {

    constructor(sessionLog: SessionLog) :
            this(userId = sessionLog.userId, token = sessionLog.token)
}
