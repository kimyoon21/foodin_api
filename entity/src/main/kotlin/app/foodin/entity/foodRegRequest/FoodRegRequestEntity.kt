package app.foodin.entity.foodRegRequest

import app.foodin.common.enums.Status
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "food_reg_requests")
data class FoodRegRequestEntity(
    val name: String,
    val categoryId: Long
) : BaseEntity<FoodRegRequest>() {

    var companyName: String? = null

    var price: Int = 0

    var summary: String? = null

    var tags: String? = null

    var mainImageUri: String? = null
    @Column(columnDefinition = "TEXT")
    var imageUris: String? = null

    var status: Status = Status.WAIT

    @Column(name = "write_user_id")
    var writeUserId: Long? = null

    @ManyToOne
    @JoinColumn(name = "write_user_id", insertable = false, updatable = false)
    lateinit var writeUserEntity: UserEntity

    constructor(foodRegRequest: FoodRegRequest) : this(foodRegRequest.name, foodRegRequest.categoryId) {
        setBaseFieldsFromDomain(foodRegRequest)
        price = foodRegRequest.price
        companyName = foodRegRequest.companyName
        mainImageUri = foodRegRequest.mainImageUri
        imageUris = foodRegRequest.imageUriList.listToCsv()
        summary = foodRegRequest.summary
        tags = foodRegRequest.tagList.listToTags()
        writeUserEntity = UserEntity(foodRegRequest.writeUser!!)
        writeUserId = writeUserEntity.id
        status = foodRegRequest.status
    }

    override fun toDomain(): FoodRegRequest {
        return FoodRegRequest(name = this.name, categoryId = this.categoryId).also {
            setDomainBaseFieldsFromEntity(it)
            it.price = this.price
            it.companyName = this.companyName
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUris.csvToList()
            it.summary = this.summary
            it.tagList = this.tags.tagsToList()
            it.writeUser = writeUserEntity.toDomain()
            it.status = this.status
        }
    }
}
