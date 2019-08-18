package app.foodin.domain.user

import app.foodin.domain.badge.Badge

data class UserInfoDto(
    val id: Long,
    val username: String,
    val nickName: String?,
    val realName: String,
    val profileImageUri: String?,
    val mainBadge: Badge?
) {
    constructor(user: User) : this(user.id, user.username, user.nickName, user.realName, user.profileImageUri, user.mainBadge)
}
