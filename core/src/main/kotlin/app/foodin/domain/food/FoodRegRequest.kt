package app.foodin.domain.food

import app.foodin.common.enums.Status
import app.foodin.domain.user.User
import java.io.Serializable

data class FoodRegRequest(
    val name: String,
    val categoryId: Long

) : Serializable {

    var companyId: Long? = null

    var price: Int = 0

    var summary: String? = null

    var tagList: List<String> = listOf()

    var mainPhotoUri: String? = null

    var photoList: List<String> = listOf()

    var status: Status = Status.WAIT

    var user: User? = null
}
