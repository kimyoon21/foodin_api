package app.foodin.auth

import app.foodin.user.SnsType
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class AuthenticationRequest() {

    constructor(email: String, password: String) : this() {
        this.email = email
        this.password = password
    }

    var snsType: SnsType = SnsType.EMAIL

    var uid: String? = null

    var email: String? = null

    var password: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var name: String? = null

    @get:Size(min = 6, max = 6, message = "{validation.size.birthday}")
    var birthday: String? = null

    @get:Pattern(regexp = "[0-9]", message = "{validation.pattern.sex}")
    var sex: String? = null
}
