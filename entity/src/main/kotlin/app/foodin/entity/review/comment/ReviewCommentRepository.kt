package app.foodin.entity.review.comment

import app.foodin.core.gateway.ReviewCommentGateway
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.review.ReviewComment
import app.foodin.entity.comment.ReviewCommentFilterQuery
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface ReviewCommentRepository : BaseRepositoryInterface<ReviewCommentEntity>

@Component
class JpaReviewCommentRepository(private val repository: ReviewCommentRepository) :
        BaseRepository<ReviewComment, ReviewCommentEntity, CommentFilter>(repository), ReviewCommentGateway {
    override fun saveFrom(t: ReviewComment): ReviewComment {
        return repository.saveAndFlush(ReviewCommentEntity(t)).toDomain()
    }

    override fun findAllByFilter(filter: CommentFilter, pageable: Pageable): Page<ReviewComment> {
        return findAll(ReviewCommentFilterQuery(filter), pageable)
    }
}