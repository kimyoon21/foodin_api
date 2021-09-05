package app.foodin.entity.recipeComment

import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.recipeComment.PostComment
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
interface PostCommentRepository : BaseRepositoryInterface<PostCommentEntity> {
    @Modifying
    @Query("UPDATE PostCommentEntity set loveCount = loveCount + :count where id = :id")
    fun addLoveCount(@Param("id") id: Long, @Param("count") count: Int)
}

@Component
class JpaRecipeCommentRepository(private val repository: PostCommentRepository) :
        BaseRepository<PostComment, PostCommentEntity, PostCommentFilter>(repository),
        RecipeCommentGateway {
    override fun addLoveCount(id: Long, count: Int) {
        repository.addLoveCount(id, count)
    }

    override fun findAllByFilter(filter: PostCommentFilter, pageable: Pageable):
            Page<PostComment> {
                return findAll(PostCommentFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: PostComment): PostComment {
                return repository.saveAndFlush(PostCommentEntity(t)).toDomain()
    }
}
