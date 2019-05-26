package app.foodin.common.utils

import app.foodin.common.enums.SnsType
import app.foodin.common.enums.UserAuthority
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_AUTH_FAILED
import app.foodin.common.utils.MDCUtils.USER_INFO_MDC

data class CustomJwtUserInfo(
    val id: Long = 0,
    val username: String = "",
    val nickName: String? = null,
    val realName: String = "",
    val snsType: SnsType? = null,
    val roles: List<UserAuthority> = emptyList()
) {
    fun hasUserRole(): Boolean {
        return roles.indexOf(UserAuthority.ROLE_USER) >= 0
    }

    fun hasAdminRole(): Boolean {
        return roles.indexOf(UserAuthority.ROLE_ADMIN) >= 0
    }
}

/****
 *Jwt 토큰에서 읽혀 Context 를 통해 MDC 에 저장되어있는 로컬쓰레드의 유저정보
 */
fun getAuthenticatedUserInfo(): CustomJwtUserInfo {
    return MDCUtils[USER_INFO_MDC]?.let {
        return JsonUtils.fromJson(it, CustomJwtUserInfo::class.java)
    } ?: throw CommonException(EX_AUTH_FAILED)
}