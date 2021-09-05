package app.foodin.domain.recipe

import app.foodin.common.enums.Status

data class PostUserDto(

    var id: Long = 0,

    var writeUserId: Long? = null,

    var writeUserNickName: String? = null,

    var status: Status? = null,

    var ratingAvg: Float? = null,

    var mainImageUri: String? = null,

    var tagList: List<String> = listOf(),

    var loveCount: Int = 0,

    var commentCount: Int = 0
)
