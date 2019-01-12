package app.foodin.domain.sessionLog

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class SessionLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @CreationTimestamp
        val createTime: Timestamp? = null,
        val userId: Long,
        val token: String


) {



}

