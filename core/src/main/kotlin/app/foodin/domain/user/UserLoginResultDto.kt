package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import java.sql.Timestamp

class UserLoginResultDto(
    val id: Long,
    val email: String?,
    val realName: String,
    val nickName: String,
    val snsType: SnsType,
    var accessToken: String,
    var refreshToken: String,
    var expireTime: Timestamp

) {

    constructor(user: User, accessToken: String, refreshToken: String, expireTime: Timestamp) : this(
            id = user.id,
            email = user.email,
            realName = user.realName,
            nickName = user.nickName ?: user.realName,
            snsType = user.snsType,
            accessToken = accessToken,
            refreshToken = refreshToken,
            expireTime = expireTime)
}
