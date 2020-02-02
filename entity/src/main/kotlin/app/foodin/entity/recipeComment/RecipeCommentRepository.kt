package app.foodin.entity.recipeComment

import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.domain.recipeComment.RecipeCommentFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface RecipeCommentRepository : BaseRepositoryInterface<RecipeCommentEntity>

@Component
class JpaRecipeCommentRepository(private val repository: RecipeCommentRepository) :
        BaseRepository<RecipeComment, RecipeCommentEntity, RecipeCommentFilter>(repository),
        RecipeCommentGateway {
    override fun findAllByFilter(filter: RecipeCommentFilter, pageable: Pageable):
            Page<RecipeComment> {
                return findAll(RecipeCommentFilterQuery(filter), pageable)

    }

    override fun saveFrom(recipeComment: RecipeComment): RecipeComment {
                return repository.saveAndFlush(RecipeCommentEntity(recipeComment)).toDomain()

    }
}
