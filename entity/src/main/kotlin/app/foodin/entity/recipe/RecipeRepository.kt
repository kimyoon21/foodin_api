package app.foodin.entity.recipe

import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.domain.recipe.RecipeInfoDto
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.StatusRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : BaseRepositoryInterface<RecipeEntity> {

    @Modifying
    @Query("UPDATE RecipeEntity set loveCount = loveCount + :count where id = :id")
    fun addLoveCount(@Param("id") id: Long, @Param("count") count: Int)
    @Modifying
    @Query("UPDATE RecipeEntity set commentCount = commentCount + :count where id = :id")
    fun addCommentCount(@Param("id") id: Long, @Param("count") count: Int)
}

@Component
class JpaRecipeRepository(private val repository: RecipeRepository) : StatusRepository<Recipe,
        RecipeEntity, RecipeFilter>(repository), RecipeGateway {
    override fun saveFrom(t: Recipe): Recipe {
        return repository.saveAndFlush(RecipeEntity(t)).toDomain()
    }

    override fun findDtoBy(filter: RecipeFilter, pageable: Pageable): Page<RecipeInfoDto> {
        return repository.findAll(RecipeFilterQuery(filter).toSpecification(), pageable).map { e -> e.toDto() }
    }

    override fun addLoveCount(id: Long, count: Int) {
        repository.addLoveCount(id, count)
    }

    override fun addCommentCount(id: Long, count: Int) {
        repository.addCommentCount(id, count)
    }

    override fun findAllByFilter(filter: RecipeFilter, pageable: Pageable): Page<Recipe> {
        return findAll(RecipeFilterQuery(filter), pageable)
    }
}
