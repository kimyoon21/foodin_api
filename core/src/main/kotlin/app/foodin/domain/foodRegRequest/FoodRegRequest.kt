package app.foodin.domain.foodRegRequest

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User

data class FoodRegRequest(
    override var id: Long = 0L,
    val name: String,
    val categoryId: Long
) : BaseDomain(id) {
    var companyId: Long? = null

    var price: Int = 0

    var summary: String? = null

    var tagList: List<String> = listOf()

    var mainPhotoUri: String? = null

    var photoList: List<String> = listOf()

    var status: Status = Status.WAIT

    var user: User? = null
}