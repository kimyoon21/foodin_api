package app.foodin.entity.recipe

import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.entity.common.BaseFilterQuery
import org.springframework.data.jpa.domain.Specification

class RecipeFilterQuery(val filter: RecipeFilter) : BaseFilterQuery<Recipe, RecipeEntity> {
    override fun toSpecification(): Specification<RecipeEntity> {
                TODO("not implemented")
    }
}
