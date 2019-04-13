package app.foodin.entity.recipe

import app.foodin.domain.recipe.Recipe
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "recipe")
data class RecipeEntity(val writerId: Long) : BaseEntity<Recipe>() {
    override fun toDomain(): Recipe {
                TODO("not implemented")
    }
}
