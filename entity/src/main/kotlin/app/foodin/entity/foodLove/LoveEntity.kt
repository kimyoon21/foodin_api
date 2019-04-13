package app.foodin.entity.foodLove

import app.foodin.domain.foodLove.Love
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.recipe.RecipeEntity
import app.foodin.entity.review.ReviewEntity
import app.foodin.entity.user.UserEntity
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "love")
data class LoveEntity(
        @Column(name = "food_id",insertable = false, updatable = false)
        val foodId: Long?,
        @Column(name = "review_id",insertable = false, updatable = false)
        val reviewId: Long?,
        @Column(name = "recipe_id",insertable = false, updatable = false)
        val recipeId: Long?,
        @Column(name = "user_id",insertable = false, updatable = false)
        val userId: Long) : BaseEntity<Love>() {

    @ManyToOne
    @Where(clause = "status = 1")
    lateinit var food: FoodEntity

    @ManyToOne
    @Where(clause = "status = 1")
    lateinit var review: ReviewEntity

    @ManyToOne
    @Where(clause = "status = 1")
    lateinit var recipe: RecipeEntity

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Where(clause = "enable = 1")
    lateinit var user: UserEntity

    constructor(love: Love) : this(
            love.foodId,
            love.reviewId,
            love.recipeId,
            love.userId)

    override fun toDomain(): Love {
        return Love(foodId = this.foodId, userId = this.userId).also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime)
            it.food = this.food.toDomain()
            it.user = this.user.toDomain()
        }
    }
}
