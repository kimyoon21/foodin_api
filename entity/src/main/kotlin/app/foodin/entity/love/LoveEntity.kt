package app.foodin.entity.love

import app.foodin.domain.love.Love
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.recipe.RecipeEntity
import app.foodin.entity.review.ReviewEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "love")
data class LoveEntity(
    @Column(name = "food_id")
    val foodId: Long?,
    @Column(name = "review_id")
    val reviewId: Long?,
    @Column(name = "recipe_id")
    val recipeId: Long?,
    @Column(name = "user_id")
    val userId: Long
) : BaseEntity<Love>() {

    @ManyToOne
    @JoinColumn(name = "food_id", insertable = false, updatable = false)
    var food: FoodEntity? = null

    @ManyToOne
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    var review: ReviewEntity? = null

    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    var recipe: RecipeEntity? = null

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    var user: UserEntity? = null

    constructor(love: Love) : this(
            love.foodId,
            love.reviewId,
            love.recipeId,
            love.userId)

    override fun toDomain(): Love {
        return Love(
                foodId = this.foodId,
                reviewId = this.reviewId,
                recipeId = this.recipeId,
                userId = this.userId).also {
            setDomainBaseFieldsFromEntity(it)
            it.food = this.food?.toDomain()
            it.review = this.review?.toDomain()
            it.recipe = this.recipe?.toDomain()
            it.user = this.user?.toDomain()
        }
    }
}
