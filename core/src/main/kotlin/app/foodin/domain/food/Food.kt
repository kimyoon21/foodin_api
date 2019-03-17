package app.foodin.domain.food

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.writable.UserWritable

data class Food(
    override var id: Long = 0,
    var name: String,
    var categoryId: Long

) : BaseDomain(id), UserWritable {

    override var writeUser: User? = null

    override var writeUserId: Long? = null

    var companyId: Long? = null

    var companyName: String? = null

    var sellerNameList: MutableList<String> = mutableListOf()

    var minPrice: Int? = 0

    var maxPrice: Int? = 0

    var summary: String? = null

    var tagList: MutableList<String> = mutableListOf()

    var mainImageUri: String? = null

    var imageUriList: MutableList<String> = mutableListOf()

    var loveCount: Int = 0

    var ratingCount: Int = 0

    var reviewCount: Int = 0

    var recipeCount: Int = 0

    var ratingAvg: Float? = null

    var status: Status? = null
}
