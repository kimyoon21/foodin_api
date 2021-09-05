package app.foodin.entity.review.comment

import app.foodin.core.gateway.ReviewCommentGateway
import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.review.ReviewComment
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
interface ReviewCommentRepository : BaseRepositoryInterface<ReviewCommentEntity> {
    @Modifying
    @Query("UPDATE ReviewCommentEntity set loveCount = loveCount + :count where id = :id")
    fun addLoveCount(@Param("id") id: Long, @Param("count") count: Int)
}

@Component
class JpaReviewCommentRepository(private val repository: ReviewCommentRepository) :
        BaseRepository<ReviewComment, ReviewCommentEntity, PostCommentFilter>(repository), ReviewCommentGateway {
    override fun addLoveCount(id: Long, count: Int) {
        repository.addLoveCount(id, count)
    }

    override fun saveFrom(t: ReviewComment): ReviewComment {
        return repository.saveAndFlush(ReviewCommentEntity(t)).toDomain()
    }

    override fun findAllByFilter(filter: PostCommentFilter, pageable: Pageable): Page<ReviewComment> {
        return findAll(ReviewCommentFilterQuery(filter), pageable)
    }
}
