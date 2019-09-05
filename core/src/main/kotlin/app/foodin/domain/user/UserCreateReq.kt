package app.foodin.domain.user

import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.common.utils.DateHelper
import javax.validation.constraints.Size

class UserCreateReq(
    val email: String,
    val realName: String,
    val snsType: SnsType,
    val nickName: String
) {

    var loginPw: String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    @get:Size(min = 8, max = 8, message = "{validation.size.birthday}")
    var birthday: String? = null

    var gender: Gender? = null

    fun toUser(): User {
        User(0, this.email, this.realName, this.snsType).let {
            it.nickName = this.nickName
            it.loginPw = this.loginPw
            it.snsUserId = this.snsUserId
            it.agreePolicy = this.agreePolicy
            it.agreeMarketing = this.agreeMarketing
            it.birthFullDay = birthday?.let { day -> DateHelper.parse(day) }
            it.birthYear = it.birthFullDay?.year
            it.gender = this.gender

            return it
        }
    }
}
