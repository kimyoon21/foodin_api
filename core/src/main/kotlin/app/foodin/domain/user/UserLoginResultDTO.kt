package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import java.sql.Timestamp

class UserLoginResultDTO(
    val email: String,
    val realName: String,
    val nickName: String,
    val snsType: SnsType,
    var accessToken: String,
    var refeshToken: String,
    var expireTime: Timestamp

) {

    constructor(user: User, accessToken: String, refeshToken: String, expireTime: Timestamp) : this(
            email = user.email,
            realName = user.realName,
            nickName = user.nickName ?: user.realName,
            snsType = user.snsType,
            accessToken = accessToken,
            refeshToken = refeshToken,
            expireTime = expireTime)
}
