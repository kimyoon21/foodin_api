package app.foodin.entity.recipe

import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : BaseRepositoryInterface<RecipeEntity>

@Component
class JpaRecipeRepository(private val repository: RecipeRepository) : BaseRepository<Recipe,
        RecipeEntity, RecipeFilter>(repository), RecipeGateway {
    override fun saveFrom(recipe: Recipe): Recipe {
                TODO("not implemented")
    }
}
