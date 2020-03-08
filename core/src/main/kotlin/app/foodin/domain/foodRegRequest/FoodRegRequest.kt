package app.foodin.domain.foodRegRequest

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto

data class FoodRegRequest(
    override var id: Long = 0L,
    val name: String,
    val categoryId: Long
) : BaseDomain(id) {
    var companyName: String? = null

    var price: Int = 0

    var summary: String? = null

    var tagList: List<String> = listOf()

    var mainImageUri: String? = null

    var imageUriList: List<String> = listOf()

    var status: Status = Status.WAIT

    var writeUser: User? = null

    fun getWriteUserInfo(): UserInfoDto? {
        return writeUser?.let {
            UserInfoDto(it)
        }
    }
}
