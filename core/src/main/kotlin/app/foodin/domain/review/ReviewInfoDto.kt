package app.foodin.domain.review

import app.foodin.common.enums.Status
// TODO 리스트에서 쓰기엔 domain 과 거의 동일, 어디에 쓰일지 확실해지면 다시 적용
data class ReviewInfoDto(
    var id: Long = 0,

    val foodId: Long? = null,

    var foodName: String? = null,

    var writeUserId: Long? = null,

    var status: Status? = null,

    var price: Int? = 0,

    var mainImageUri: String? = null,

    var loveCount: Int = 0,

    var commentCount: Int? = null,

    var rating: Float = 0F
)
