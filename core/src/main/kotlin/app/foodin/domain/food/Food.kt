package app.foodin.domain.food

import app.foodin.core.domain.Status
import app.foodin.domain.`interface`.Writable
import java.io.Serializable
import java.sql.Timestamp

data class Food(
        val name: String,
        val categoryId: Long


) : Writable, Serializable {

    var id: Long? = null

    var createdTime: Timestamp? = null

    var companyId : Long? = null

    var minPrice : Int = 0

    var maxPrice : Int = 0

    var summary : String? = null

    var tagList : List<String> = listOf()

    var mainPhotoUri : Int = 0

    var photoList : List<String> = listOf()

    var loveCount : Int = 0

    var ratingCount : Int = 0

    var reviewCount : Int = 0

    var recipeCount : Int = 0

    var rating : Float? = null

    var firstWriterId : Long? = null

    var status : Status? = null

}

