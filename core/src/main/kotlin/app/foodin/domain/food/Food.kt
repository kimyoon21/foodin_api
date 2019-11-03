package app.foodin.domain.food

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.modelmapper.ModelMapper

data class Food(
    override var id: Long = 0,
    var name: String,
    var categoryId: Long

) : BaseDomain(id), UserWritable {

    @JsonIgnore
    override var writeUser: User? = null

    override var writeUserId: Long? = null

    fun getWriteUserInfo(): UserInfoDto? {
        return writeUser?.let {
            UserInfoDto(it)
        }
    }

    var companyId: Long? = null

    var companyName: String? = null

    var sellerNameList: List<String> = listOf()

    var minPrice: Int? = 0

    var maxPrice: Int? = 0

    var summary: String? = null

    var tagList: List<String> = listOf()

    var mainImageUri: String? = null

    var imageUriList: List<String> = listOf()

    var loveCount: Int = 0

    var ratingCount: Int = 0

    var reviewCount: Int = 0

    var recipeCount: Int = 0

    var ratingAvg: Float? = null

    var status: Status? = null

    var foundUserList: List<UserInfoDto> = listOf()

    // for my
    var hasLoved = false
    var hasReview = false
    var hasRecipe = false

    fun toDto(): FoodInfoDto {
        return ModelMapper().map(this, FoodInfoDto::class.java)
    }
}
