package app.foodin.domain.food

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.writable.UserWritable

data class Food(
    override var id: Long? = null,
    var name: String,
    var categoryId: Long

) : BaseDomain(id), UserWritable {

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

    var rating: Float? = null

    var firstWriterId: Long? = null

    var status: Status? = null
}
