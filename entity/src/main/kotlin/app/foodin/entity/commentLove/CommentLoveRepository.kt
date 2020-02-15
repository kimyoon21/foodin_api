package app.foodin.entity.commentLove

import app.foodin.core.gateway.CommentLoveGateway
import app.foodin.domain.commentLove.CommentLove
import app.foodin.domain.commentLove.CommentLoveFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.toDomainList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface CommentLoveRepository : BaseRepositoryInterface<CommentLoveEntity> {
    fun findByReviewCommentIdAndUserId(reviewCommentId: Long, userId: Long): CommentLoveEntity?
    fun findAllByReviewCommentId(reviewCommentId: Long): List<CommentLoveEntity>
    fun findAllByUserIdAndReviewCommentIdIn(userId: Long, reviewCommentIds: MutableList<Long>): List<CommentLoveEntity>
    fun findAllByUserIdAndRecipeCommentIdIn(userId: Long, recipeCommentIds: MutableList<Long>): List<CommentLoveEntity>
}

@Component
class JpaCommentLoveRepository(private val repository: CommentLoveRepository) :
        BaseRepository<CommentLove, CommentLoveEntity, CommentLoveFilter>(repository),
        CommentLoveGateway {
    override fun findAllByReviewCommentId(reviewCommentId: Long): List<CommentLove> {
        return repository.findAllByReviewCommentId(reviewCommentId).toDomainList()
    }

    override fun findAllByUserIdAndReviewCommentIdIn(userId: Long, reviewCommentIds: MutableList<Long>): List<CommentLove> {
        return repository.findAllByUserIdAndReviewCommentIdIn(userId, reviewCommentIds).toDomainList()
    }

    override fun findAllByUserIdAndRecipeCommentIdIn(userId: Long, recipeCommentIds: MutableList<Long>): List<CommentLove> {
        return repository.findAllByUserIdAndRecipeCommentIdIn(userId, recipeCommentIds).toDomainList()
    }

    override fun findByReviewCommentIdAndUserId(reviewCommentId: Long, userId: Long): CommentLove? {
        return repository.findByReviewCommentIdAndUserId(reviewCommentId, userId)?.toDomain()
    }

    override fun findAllByFilter(filter: CommentLoveFilter, pageable: Pageable): Page<CommentLove> {
                return findAll(CommentLoveFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: CommentLove): CommentLove {
                return repository.saveAndFlush(CommentLoveEntity(t)).toDomain()
    }
}
