package app.foodin.core.gateway

import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.domain.recipe.RecipeInfoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecipeGateway : StatusGateway<Recipe, RecipeFilter> {
    fun findDtoBy(filter: RecipeFilter, pageable: Pageable): Page<RecipeInfoDto>
}
