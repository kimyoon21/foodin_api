package app.foodin.domain.user

import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.validation.constraints.Size
class UserRegisterDTO(
        val email: String,
        val name: String,
        val snsType: SnsType
) {

    fun toUser(): User {
        User(this.email, this.name, this.snsType).let{

            it.loginPw = this.loginPw
            it.snsUserId = this.snsUserId
            it.agreePolicy = this.agreePolicy
            it.agreeMarketing = this.agreeMarketing
            val formatter = DateTimeFormatter.ofPattern("yyMMdd", Locale.KOREAN)
            it.birthFullDay = LocalDate.parse(birthday, formatter)
            it.sex = this.sex

            return it
        }
    }

    var loginPw : String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    @get:Size(min = 6, max = 6, message = "{validation.size.birthday}")
    var birthday: String? = null

    var sex: Sex? = null
}
