package app.foodin.domain.review

import app.foodin.common.enums.Status
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.domain.comment.Commentable
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodInfoDto
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.modelmapper.ModelMapper

data class Review(
    override var id: Long = 0,
    var foodId: Long
) : StatusDomain(id), UserWritable, Commentable {

    @JsonIgnore
    override var writeUser: User? = null

    override var writeUserId: Long? = null

    fun getWriteUserInfo() = writeUser?.let { UserInfoDto(it) }

    var food: FoodInfoDto? = null

    var price: Int? = null

    var contents: String? = null

    var tagList: List<String> = listOf()

    var mainImageUri: String? = null

    var imageUriList: List<String> = listOf()

    var loveCount: Int = 0

    var commentCount: Int = 0

    var rating: Float = 0F

    var updated: Boolean = false

    constructor(food: Food, writer: User, reviewReq: ReviewReq) : this(foodId = food.id) {
        setFromRequest(reviewReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.foodId = food.id
        this.food = food.toDto()
        this.status = Status.APPROVED
    }

    override fun setFromRequest(request: Any) {
        if (request is ReviewReq) {
            request.let {
                this.imageUriList = it.imageUriList
                this.tagList = it.tagList
                this.contents = it.contents
                this.mainImageUri = it.mainImageUri
                this.rating = it.rating
                this.price = it.price
            }
        } else {
            throw CommonException(EX_INVALID_REQUEST)
        }
    }

    fun createFromReq(reviewCreateReq: ReviewCreateReq): Review {
        val entity = ModelMapper().map(reviewCreateReq, Review::class.java).also {
            it.status = Status.APPROVED
        }
        return entity
    }
}
