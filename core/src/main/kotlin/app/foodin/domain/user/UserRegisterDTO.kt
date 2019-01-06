package app.foodin.domain.user

import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import javax.validation.constraints.Size
class UserRegisterDTO(
        val email: String,
        val name: String,
        val snsType: SnsType
) {

    fun toUser(): User {
        return User(this.email, this.name, this.snsType).apply {
            password = this.password
            snsUserId = this.snsUserId
            agreePolicy = this.agreePolicy
            agreeMarketing = this.agreeMarketing
            birthday = this.birthday
            sex = this.sex
        }
    }

    var password : String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    @get:Size(min = 6, max = 6, message = "{validation.size.birthday}")
    var birthday: String? = null

    var sex: Sex? = null
}
