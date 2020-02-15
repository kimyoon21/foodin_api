package app.foodin.domain.review

import app.foodin.common.enums.Status

data class ReviewInfoDto(
    var id: Long = 0,

    var foodId: Long? = null,

    var foodName: String? = null,

    var writeUserId: Long? = null,

    var writeUserNickName: String? = null,

    var status: Status? = null,

    var price: Int? = 0,

    var mainImageUri: String? = null,

    var loveCount: Int = 0,

    var commentCount: Int? = null,

    var rating: Float = 0F
)
