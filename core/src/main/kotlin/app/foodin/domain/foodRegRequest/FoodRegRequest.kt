package app.foodin.domain.foodRegRequest

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User

data class FoodRegRequest(
    override var id: Long = 0L,
    val name: String,
    val categoryId: Long
) : BaseDomain(id) {
    var companyName: String? = null

    var price: Int = 0

    var summary: String? = null

    var tagList: MutableList<String> = mutableListOf()

    var mainImageUri: String? = null

    var imageUriList: MutableList<String> = mutableListOf()

    var status: Status = Status.WAIT

    var writeUser: User? = null
}