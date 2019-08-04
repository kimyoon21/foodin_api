package app.foodin.entity.food

import app.foodin.common.enums.Status
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodInfoDTO
import app.foodin.entity.common.BaseEntity
import org.modelmapper.ModelMapper
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "food")
data class FoodEntity(
    var name: String,
    var categoryId: Long
) : BaseEntity<Food>() {
    /** 판매처 너무 많고 부정확해서 일단은 그냥 이름사용 */
    var companyId: Long? = null

    var companyName: String? = null
    /** 판매처 이름 CSV **/
    var sellerNames: String? = null
    // TODO 다대다 연결
//    var sellers : Set<SellerEntity>? = setOf()

    var minPrice: Int? = 0

    var maxPrice: Int? = 0

    var summary: String? = null

    var tags: String? = null

    var mainImageUri: String? = null

    var imageUris: String? = null

    var loveCount: Int = 0

    var ratingCount: Int = 0

    var reviewCount: Int = 0

    var recipeCount: Int = 0

    var ratingAvg: Float? = null

    var writeUserId: Long? = null

    var status: Status? = null

    constructor(food: Food) : this(food.name, food.categoryId) {

        companyId = food.companyId
        companyName = food.companyName
        categoryId = food.categoryId
        sellerNames = food.sellerNameList.listToCsv()
        minPrice = food.minPrice
        maxPrice = food.maxPrice
        summary = food.summary
        tags = food.tagList.listToTags()
        mainImageUri = food.mainImageUri
        imageUris = food.imageUriList.listToCsv()
        loveCount = food.loveCount
        ratingCount = food.ratingCount
        reviewCount = food.reviewCount
        recipeCount = food.recipeCount
        ratingAvg = food.ratingAvg
        writeUserId = food.writeUserId
        status = food.status
    }

    override fun toDomain(): Food {
        return Food(name = this.name, categoryId = this.categoryId).also {
            setDomainBaseFieldsFromEntity(it)
            it.companyId = this.companyId
            it.companyName = this.companyName
            it.sellerNameList = this.sellerNames.csvToList()
            it.minPrice = this.minPrice
            it.maxPrice = this.maxPrice
            it.summary = this.summary
            it.tagList = this.tags.tagsToList()
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUris.csvToList()
            it.loveCount = this.loveCount
            it.ratingCount = this.ratingCount
            it.reviewCount = this.reviewCount
            it.recipeCount = this.recipeCount
            it.ratingAvg = this.ratingAvg
            it.writeUserId = this.writeUserId
            it.status = this.status
        }
    }

    fun toDto(): FoodInfoDTO {
        return ModelMapper().map(this, FoodInfoDTO::class.java)
    }
}