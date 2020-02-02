package app.foodin.entity.recipe

import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.domain.recipe.RecipeInfoDto
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.StatusRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : BaseRepositoryInterface<RecipeEntity>

@Component
class JpaRecipeRepository(private val repository: RecipeRepository) : StatusRepository<Recipe,
        RecipeEntity, RecipeFilter>(repository), RecipeGateway {
    override fun saveFrom(t: Recipe): Recipe {
        return repository.saveAndFlush(RecipeEntity(t)).toDomain()
    }

    override fun findDtoBy(filter: RecipeFilter, pageable: Pageable): Page<RecipeInfoDto> {
        return repository.findAll(RecipeFilterQuery(filter).toSpecification(), pageable).map { e -> e.toDto() }
    }

    override fun findAllByFilter(filter: RecipeFilter, pageable: Pageable): Page<Recipe> {
        return findAll(RecipeFilterQuery(filter), pageable)
    }
}
