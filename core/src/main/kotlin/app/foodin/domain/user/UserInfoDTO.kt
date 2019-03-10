package app.foodin.domain.user

import app.foodin.domain.badge.Badge

data class UserInfoDTO(
    val id: Long,
    val nickName: String?,
    val realName: String,
    val profileImageUri: String?,
    val mainBadge: Badge?
) {
    constructor(user: User) : this(user.id, user.nickName, user.realName, user.profileImageUri, user.mainBadge)
}
