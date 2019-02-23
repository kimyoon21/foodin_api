package app.foodin.entity.foodCategory

import app.foodin.common.enums.Status
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.domain.food.Food
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.food.FoodEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "food")
data class FoodCategoryEntity(
        var name: String,
        var categoryId: Long
) : BaseEntity<FoodCategory>(){


    var companyId : Long? = null

    var minPrice : Int = 0

    var maxPrice : Int = 0

    var summary : String? = null

    var tags : String? = null

    var mainPhotoUri : String? = null

    var photos : String? = null

    var loveCount : Int = 0

    var ratingCount : Int = 0

    var reviewCount : Int = 0

    var recipeCount : Int = 0

    var rating : Float? = null

    var firstWriterId : Long? = null

    var status : Status? = null

    constructor(food: Food) : this(food.name, food.categoryId) {

        companyId = food.companyId
        categoryId = food.categoryId
        minPrice = food.minPrice
        maxPrice = food.maxPrice
        summary = food.summary
        tags = food.tagList.listToCsv()
        mainPhotoUri = food.mainPhotoUri
        photos = food.photoList.listToCsv()
        loveCount = food.loveCount
        ratingCount = food.ratingCount
        reviewCount = food.reviewCount
        recipeCount = food.recipeCount
        rating = food.rating
        firstWriterId = food.firstWriterId
        status = food.status
    }

    override fun toDomain(): FoodCategory {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
