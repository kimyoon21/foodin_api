package app.foodin.entity.user

import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var email: String,
    var name: String,
    @Enumerated(EnumType.STRING)
    var snsType : SnsType


)

fun UserEntity.toUser(): User {
    return User(email = this.email, name = this.name, snsType = this.snsType)
}
