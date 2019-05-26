package app.foodin.entity.foodCategory

import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "food_category")
data class FoodCategoryEntity(
    var groupName: String,
    var filterName: String,
    var detailName: String
) : BaseEntity<FoodCategory>() {

    val foodCount: Int = 0

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userFoodCategoryEntityList")
    val userList: List<UserEntity> = listOf()

    constructor(foodCategory: FoodCategory) : this(foodCategory.groupName,
            foodCategory.filterName,
            foodCategory.detailName)

    override fun toDomain(): FoodCategory {
        return FoodCategory(groupName = this.groupName,
                filterName = this.filterName,
                detailName = this.detailName).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.foodCount = this.foodCount
        }
    }
}
