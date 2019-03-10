package app.foodin.entity.review

import app.foodin.common.enums.Status
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.domain.review.Review
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "review")
data class ReviewEntity(
    val foodId: Long
) : BaseEntity<Review>() {

    var status: Status? = null

    var price: Int? = 0

    var contents: String? = null

    var tags: String? = null

    var mainImageUri: String? = null

    var imageUris: String? = null

    var loveCount: Int = 0

    var commentCount: Int? = null

    var rating: Int = 0

    var writeUserId: Long? = null

    constructor(review: Review) : this(review.foodId) {
        price = review.price
        contents = review.contents
        tags = review.tagList.listToCsv()
        mainImageUri = review.mainImageUri
        imageUris = review.imageUriList.listToCsv()
        loveCount = review.loveCount
        commentCount = review.commentCount
        rating = review.rating
        writeUserId = review.writeUserId
        status = review.status
    }

    override fun toDomain(): Review {
        return Review(foodId = this.foodId).also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime)
            it.price = this.price
            it.contents = this.contents
            it.tagList = this.tags.csvToList()
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUris.csvToList()
            it.loveCount = this.loveCount
            it.commentCount = this.commentCount
            it.rating = this.rating
            it.writeUserId = this.writeUserId
            it.status = this.status
        }
    }
}