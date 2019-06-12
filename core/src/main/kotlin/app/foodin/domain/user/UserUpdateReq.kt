package app.foodin.domain.user

import app.foodin.common.enums.Gender
import javax.validation.constraints.Size

class UserUpdateReq() {

    var nickName: String? = null
    var realName: String? = null

    var loginPw: String? = null
    var loginPwCheck: String? = null

//    var agreeMarketing: Boolean = false

    @get:Size(min = 8, max = 8, message = "{validation.size.birthday}")
    var birthday: String? = null

    var gender: Gender? = null
}
