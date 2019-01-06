package app.foodin.domain.user

import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import javax.validation.constraints.Size

data class User(
        val email: String,
        val name: String,
        val snsType: SnsType

){

    var password : String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    @get:Size(min = 6, max = 6, message = "{validation.size.birthday}")
    var birthday: String? = null

    var sex: Sex? = null
}

