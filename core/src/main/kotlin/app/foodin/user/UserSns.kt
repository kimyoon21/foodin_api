package app.foodin.user

import app.foodin.domain.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
class UserSns(
    @Enumerated(EnumType.STRING)
    var snsType: SnsType,

    var uid: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0

    @Version
    private var version: Int = 0

    @field:CreationTimestamp
    private lateinit var createTime: Timestamp

    @field:UpdateTimestamp
    private lateinit var updateTime: Timestamp


    @ManyToOne(fetch = FetchType.LAZY)
    private var user: User? = null

    fun setUser(user: User) {
        this.user = user
    }
}
