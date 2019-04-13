package app.foodin.entity.foodLove

import app.foodin.domain.food.Food
import app.foodin.domain.foodLove.FoodLove
import app.foodin.domain.user.User
import app.foodin.entity.common.BaseEntity
import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "food_love")
data class FoodLoveEntity(
        val foodId: Long,
        val userId: Long) : BaseEntity<FoodLove>() {

    @ManyToOne
    @Where(clause = "status = 1")
    lateinit var food: Food

    @ManyToOne
    @Where(clause = "enable = 1")
    lateinit var user: User

    constructor(foodLove: FoodLove) : this(
            foodLove.foodId,
            foodLove.userId)

    override fun toDomain(): FoodLove {
        return FoodLove(foodId = this.foodId, userId = this.userId).also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime)
            it.food = this.food
            it.user = this.user
        }
    }
}
