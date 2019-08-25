package app.foodin.entity.recipe

import app.foodin.domain.recipe.Recipe
import app.foodin.entity.common.StatusEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "recipes")
data class RecipeEntity(val writeUserId: Long) : StatusEntity<Recipe>() {

    constructor(recipe: Recipe) : this(recipe.writeUserId ?: 0) {
    }
    override fun toDomain(): Recipe {
        return Recipe().also {
            it.setDefaultValues(this.id, this.createdTime, this.updatedTime, this.status)
            it.writeUserId = this.writeUserId
        }
    }
}
