package app.foodin.domain.review

import app.foodin.common.enums.Status
import app.foodin.domain.comment.Commentable
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodInfoDTO
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDTO
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

    fun getWriteUserInfo() = writeUser?.let { UserInfoDTO(it) }

    var food: FoodInfoDTO? = null

    var price: Int? = null

    var contents: String? = null

    var tagList: MutableList<String> = mutableListOf()

    var mainImageUri: String? = null

    var imageUriList: MutableList<String> = mutableListOf()

    var loveCount: Int = 0

    var commentCount: Int = 0

    var rating: Float = 0F

    constructor(food: Food, writer: User, reviewReq: ReviewReq) : this(foodId = food.id) {
        setFromRequestDTO(reviewReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.foodId = food.id
        this.food = food.toDto()
        this.status = Status.APPROVED
    }

    fun setFromRequestDTO(reviewReq: ReviewReq) {
        reviewReq.let {
            this.imageUriList = it.imageUriList
            this.tagList = it.tagList
            this.contents = it.contents
            this.mainImageUri = it.mainImageUri
            this.rating = it.rating
            this.price = it.price
        }
    }

    fun createFromReq(reviewCreateReq: ReviewCreateReq): Review {
        val entity = ModelMapper().map(reviewCreateReq, Review::class.java).also {
            it.status = Status.APPROVED
        }
        return entity
    }
}