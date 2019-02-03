package app.foodin.domain.food

import java.io.Serializable
import java.sql.Timestamp

data class Food(
        val name: String,
        val categoryId: Long


) : Serializable {

    var id: Long? = null

    var createdTime: Timestamp? = null


}

