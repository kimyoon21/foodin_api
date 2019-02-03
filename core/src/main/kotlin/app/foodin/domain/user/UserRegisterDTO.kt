package app.foodin.domain.user

import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.validation.constraints.Size
class UserRegisterDTO(
        val email: String,
        val realName: String,
        val snsType: SnsType
) {

    fun toUser(): User {
        User(this.email, this.realName, this.snsType).let{

            it.loginPw = this.loginPw
            it.snsUserId = this.snsUserId
            it.agreePolicy = this.agreePolicy
            it.agreeMarketing = this.agreeMarketing
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            it.birthFullDay = LocalDate.parse(birthday, formatter)
            it.birthYear = it.birthFullDay?.year
            it.gender = this.gender

            return it
        }
    }

    var loginPw : String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    @get:Size(min = 8, max = 8, message = "{validation.size.birthday}")
    var birthday: String? = null

    var gender: Gender? = null
}
