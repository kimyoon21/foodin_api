package app.foodin.entity.session

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "session_log")
data class SessionLogEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @CreationTimestamp
        val createTime: Timestamp? = null,
        val userId: Long,
        val jwtToken: String


) {



}

