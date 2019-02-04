package app.foodin.entity.food

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.core.domain.Status
import app.foodin.domain.food.Food
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import javax.persistence.*

@Order(Ordered.HIGHEST_PRECEDENCE)
@Entity
@Table(name = "food")
data class FoodEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var name: String,
        var categoryId: Long
) {

    var companyId : Long? = null

    var minPrice : Int = 0

    var maxPrice : Int = 0

    var summary : String? = null

    var tags : String? = null

    var mainPhotoUri : Int = 0

    var photos : String? = null

    var loveCount : Int = 0

    var ratingCount : Int = 0

    var reviewCount : Int = 0

    var recipeCount : Int = 0

    var rating : Float? = null

    var firstWriterId : Long? = null

    var status : Status? = null

    constructor(food: Food) : this(null, food.name, food.categoryId) {

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

}

fun FoodEntity.toFood(): Food {
    return Food(name = this.name, categoryId = this.categoryId).also {
        it.id = this.id
        it.companyId = this.companyId
        it.minPrice = this.minPrice
        it.maxPrice = this.maxPrice
        it.summary = this.summary
        it.tagList = this.tags.csvToList()
        it.mainPhotoUri = this.mainPhotoUri
        it.photoList = this.photos.csvToList()
        it.loveCount = this.loveCount
        it.ratingCount = this.ratingCount
        it.reviewCount = this.reviewCount
        it.recipeCount = this.recipeCount
        it.rating = this.rating
        it.firstWriterId = this.firstWriterId
        it.status = this.status
    }
}