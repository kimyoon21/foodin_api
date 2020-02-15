package app.foodin.entity.recipeComment

import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface RecipeCommentRepository : BaseRepositoryInterface<RecipeCommentEntity>{
    @Modifying
    @Query("UPDATE RecipeCommentEntity set loveCount = loveCount + :count where id = :id")
    fun addLoveCount(@Param("id") id: Long, @Param("count") count: Int)
}

@Component
class JpaRecipeCommentRepository(private val repository: RecipeCommentRepository) :
        BaseRepository<RecipeComment, RecipeCommentEntity, CommentFilter>(repository),
        RecipeCommentGateway {
    override fun addLoveCount(id: Long, count: Int) {
        repository.addLoveCount(id, count)
    }

    override fun findAllByFilter(filter: CommentFilter, pageable: Pageable):
            Page<RecipeComment> {
                return findAll(RecipeCommentFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: RecipeComment): RecipeComment {
                return repository.saveAndFlush(RecipeCommentEntity(t)).toDomain()
    }
}
