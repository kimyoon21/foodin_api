package app.foodin.entity.foodRegRequest

import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import kotlin.String

@Entity
@Table(name = "food_reg_request")
data class FoodRegRequestEntity(val field1: String) : BaseEntity<FoodRegRequest>() {
    override fun toDomain(): FoodRegRequest {
                TODO("not implemented")
    }
}
