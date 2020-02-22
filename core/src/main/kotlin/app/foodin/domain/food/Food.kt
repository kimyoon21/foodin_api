package app.foodin.domain.food

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.modelmapper.ModelMapper

data class Food(
    override var id: Long = 0,
    var name: String,
    var categoryId: Long

) : StatusDomain(id), UserWritable {

    var category: FoodCategory? = null

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

    var foundUserList: List<UserInfoDto> = listOf()

    // for my
    var hasLoved = false
    var hasReview = false

    fun toDto(): FoodInfoDto {
        return ModelMapper().map(this, FoodInfoDto::class.java)
    }

    override fun setFromRequest(requestDto: Any) {
        if (requestDto is Food) {
            requestDto.let {
                this.name = it.name
                this.categoryId = it.categoryId
                this.writeUserId = it.writeUserId
                this.companyId = it.companyId
                this.companyName = it.companyName
                this.sellerNameList = it.sellerNameList
                this.minPrice = it.minPrice
                this.maxPrice = it.maxPrice
                this.summary = it.summary
                this.tagList = it.tagList
                this.mainImageUri = it.mainImageUri
                this.imageUriList = it.imageUriList
                this.loveCount = it.loveCount
                this.ratingCount = it.ratingCount
                this.reviewCount = it.reviewCount
                this.recipeCount = it.recipeCount
                this.ratingAvg = it.ratingAvg
            }
        } else {
            throw CommonException(EX_INVALID_REQUEST)
        }
    }
}
