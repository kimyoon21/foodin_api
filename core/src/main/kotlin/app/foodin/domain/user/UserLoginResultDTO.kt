package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import java.sql.Timestamp

class UserLoginResultDTO(
        val email: String,
        val name: String,
        val nickName: String,
        val snsType: SnsType,
        var accessToken: String,
        var expireTime: Timestamp

) {


    constructor(user: User, accessToken: String, expireTime: Timestamp) : this(
            email = user.email,
            name = user.name,
            nickName = user.nickName?:user.name,
            snsType = user.snsType,
            accessToken = accessToken,
            expireTime = expireTime)


}
