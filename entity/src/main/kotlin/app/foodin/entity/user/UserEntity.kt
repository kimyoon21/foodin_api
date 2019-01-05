package app.foodin.entity.user

import app.foodin.domain.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var email: String,
    var name: String
)

fun UserEntity.toUser(): User {
    return User(email = this.email, name = this.name)
}
