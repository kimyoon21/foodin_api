package app.foodin.domain.review

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.writable.UserWritable

data class Review(
    override var id: Long = 0,
    var foodId: Long

) : BaseDomain(id), UserWritable {
    override var writeUser: User? = null

    override var writeUserId: Long? = null

    var status: Status? = null

    var price: Int? = 0

    var contents: String? = null

    var tagList: MutableList<String> = mutableListOf()

    var mainImageUri: String? = null

    var imageUriList: MutableList<String> = mutableListOf()

    var loveCount: Int = 0

    var commentCount: Int? = null

    var rating: Int = 0

    constructor(reviewCreateReq: ReviewCreateReq):this(foodId = reviewCreateReq.foodId){

    }

}
