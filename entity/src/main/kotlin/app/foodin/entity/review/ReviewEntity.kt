package app.foodin.entity.review

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewInfoDto
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.user.UserEntity
import org.modelmapper.ModelMapper
import javax.persistence.*

@Entity
@Table(name = "reviews")
data class ReviewEntity(
        // TODO 푸드를 엔티티로 연결하고, 리뷰에도 푸드카테고리 필터 가능하게
    @Column(name = "food_id")
    val foodId: Long
) : StatusEntity<Review>() {
    @ManyToOne
    @JoinColumn(name = "food_id", insertable = false, updatable = false)
    var food: FoodEntity? = null

    var price: Int? = null

    var contents: String? = null

    var tags: String? = null

    var mainImageUri: String? = null

    @Column(columnDefinition = "TEXT")
    var imageUris: String? = null

    var loveCount: Int = 0

    var commentCount: Int = 0

    var rating: Float = 0F
    @Column(name = "write_user_id")
    var writeUserId: Long? = null

    @ManyToOne
    @JoinColumn(name = "write_user_id", insertable = false, updatable = false)
    lateinit var writeUserEntity: UserEntity

    constructor(review: Review) : this(review.foodId) {
        setBaseFieldsFromDomain(review)
        price = review.price
        contents = review.contents
        tags = review.tagList.listToTags()
        mainImageUri = review.mainImageUri
        imageUris = review.imageUriList.listToCsv()
        loveCount = review.loveCount
        commentCount = review.commentCount
        rating = review.rating
        writeUserId = review.writeUserId
        writeUserEntity = UserEntity(review.writeUser!!)
        status = review.status
    }

    override fun toDomain(): Review {
        return Review(foodId = this.foodId).also {
            setDomainBaseFieldsFromEntity(it)
            it.food = this.food?.toDto()
            it.price = this.price
            it.contents = this.contents
            it.tagList = this.tags.tagsToList()
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUris.csvToList()
            it.loveCount = this.loveCount
            it.commentCount = this.commentCount
            it.rating = this.rating
            it.writeUser = this.writeUserEntity.toDomain()
            it.writeUserId = this.writeUserId
            it.status = this.status
        }
    }

    fun toDto(): ReviewInfoDto {
        return ModelMapper().map(this, ReviewInfoDto::class.java).also{
            it.foodName = this.food?.name
        }
    }
}
