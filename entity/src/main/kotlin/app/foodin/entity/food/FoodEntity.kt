package app.foodin.entity.food

import app.foodin.common.enums.Status
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodInfoDto
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.foodCategory.FoodCategoryEntity
import app.foodin.entity.user.UserEntity
import org.modelmapper.ModelMapper
import javax.persistence.*

@Entity
@Table(name = "foods")
data class FoodEntity(
    var name: String,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: FoodCategoryEntity
) : StatusEntity<Food>() {
    @Column(name = "category_id", updatable = false, insertable = false)
    var categoryId: Long = category.id

    /** 판매처 너무 많고 부정확해서 일단은 그냥 이름사용 */
    var companyId: Long? = null

    var companyName: String? = null
    /** 판매처 이름 CSV **/
    var sellerNames: String? = null

    var minPrice: Int? = 0

    var maxPrice: Int? = 0

    var summary: String? = null

    var tags: String? = null

    var mainImageUri: String? = null

    @Column(columnDefinition = "TEXT")
    var imageUris: String? = null

    var loveCount: Int = 0

    var ratingCount: Int = 0

    var reviewCount: Int = 0

    var recipeCount: Int = 0

    var ratingAvg: Float? = null

    @ManyToOne
    @JoinColumn(name = "write_user_id")
    var writeUser: UserEntity? = null

    constructor(food: Food) : this(food.name, FoodCategoryEntity(food.category!!)) {
        setBaseFieldsFromDomain(food)
        companyId = food.companyId
        companyName = food.companyName
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
        writeUser = food.writeUser?.let { UserEntity(it) }
        status = food.status
    }

    override fun toDomain(): Food {
        return Food(name = this.name, categoryId = this.category.id).also {
            setDomainBaseFieldsFromEntity(it)
            it.category = this.category.toDomain()
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
            it.writeUser = this.writeUser?.toDomain()
            it.status = this.status
        }
    }

    fun toDto(): FoodInfoDto {
        return ModelMapper().map(this, FoodInfoDto::class.java)
    }
}
